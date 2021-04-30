package com.centit.kubernetes.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.centit.kubernetes.config.KubernetesProperties;
import com.centit.kubernetes.entity.Mysql;
import com.centit.kubernetes.service.DeploymentService;
import com.centit.kubernetes.service.MysqlService;
import com.centit.kubernetes.service.ServiceService;
import com.centit.kubernetes.utils.BeanUtils;
import com.centit.kubernetes.utils.TemplateEngineUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Service;

@Service
public class MysqlServiceImpl implements MysqlService {

    @Autowired
    AppsV1Api appsV1Api;

    @Autowired
    ServiceService serviceService;

    @Autowired
    DeploymentService deploymentService;

    @Autowired
    KubernetesProperties kubernetesProperties;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    String namespace;

    @PostConstruct
    public void init(){
        namespace = kubernetesProperties.getNamespace();
    }

    /**
     * 创建单实例无备份mysql 创建deployment成功后创建svc，并将初始密码与实例ID与随机端口返回
     * 
     * @throws Exception
     */
    @Override
    public Mysql createMysql(Mysql mysql) throws Exception {
        
        String image = "hub.centit.com/docker/mysql:" + mysql.getVersion();

        Map<String, Object> params = BeanUtils.convertToMap(mysql);
        params.put("namespace", namespace);
        params.put("image", image);

        String yaml = TemplateEngineUtils.binding(
                getClass().getClassLoader().getResource("templates/db/mysql-single-deployment.yaml").getFile(), params);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        V1Deployment v1Deployment = objectMapper.readValue(yaml, V1Deployment.class);

        v1Deployment = deploymentService.createDeployment(namespace, v1Deployment);

        String svcYaml = TemplateEngineUtils.binding(
                getClass().getClassLoader().getResource("templates/db/mysql-single-svc.yaml").getFile(), params);
        V1Service v1Service = serviceService.createService(namespace, objectMapper.readValue(svcYaml, V1Service.class));
        
        mysql.setPort(v1Service.getSpec().getPorts().get(0).getNodePort());
        
        
        return mysql;
    }

    @Override
    public Mysql deleteMysql(String name) throws Exception {

        deploymentService.deleteDeployment(namespace, name);

        try{
            serviceService.deleteService(namespace, name);
        }catch(ApiException e){
            if(HttpStatus.NOT_FOUND.value() == e.getCode()){
                logger.warn("数据库:" + name + "的控制器已被删除,但对应的service不存在");
            }else{
                throw e;
            }
        }

        Mysql mysql = new Mysql();
        mysql.setName(name);

        return mysql;
    }

    @Override
    public Mysql getMysql(String name) throws ApiException {

        V1Deployment v1Deployment = deploymentService.getDeployment(namespace, name);
        V1Service v1Service = serviceService.getService(namespace, name);

        Mysql mysql = new Mysql();
        mysql.setName(name);
        mysql.setPort(v1Service.getSpec().getPorts().get(0).getNodePort());
        mysql.setReady(!(Optional.ofNullable(v1Deployment.getStatus().getUnavailableReplicas()).orElse(-1) > 0));
        mysql.setVersion(v1Deployment.getMetadata().getLabels().get("version"));
        mysql.setCpu(v1Deployment.getSpec().getTemplate().getSpec().getContainers().get(0)
                .getResources().getLimits().get("cpu").toSuffixedString());
        mysql.setMemory(v1Deployment.getSpec().getTemplate().getSpec().getContainers().get(0)
                .getResources().getLimits().get("memory").toSuffixedString());
        mysql.setGmtCreate(v1Deployment.getMetadata().getCreationTimestamp().toDate()) ;
       
        return mysql;
    }

    @Override
    public List<Mysql> listMysql() throws ApiException {

        List<V1Service> v1Services = serviceService.listServices(namespace).getItems();
        List<V1Deployment> v1Deployments = deploymentService.listDeployment(namespace).getItems();

        List<Mysql> mysqls = new ArrayList<Mysql>();

        for (V1Deployment v1Deployment : v1Deployments) {
            Mysql mysql = new Mysql();
            mysql.setName(v1Deployment.getMetadata().getName());
            for (V1Service v1Service : v1Services) {
                if (v1Service.getMetadata().getName().startsWith(v1Deployment.getMetadata().getName())) {
                    mysql.setPort(v1Service.getSpec().getPorts().get(0).getNodePort());
                }
            }
            
            //单节点实例中，当有一个unavailableReplicas时，则整体为不可用状态（只要非ready状态均为unavailable）
            mysql.setReady(!(Optional.ofNullable(v1Deployment.getStatus().getUnavailableReplicas()).orElse(-1) > 0));
            mysql.setCpu(v1Deployment.getSpec().getTemplate().getSpec().getContainers().get(0).getResources()
                    .getLimits().get("cpu").toSuffixedString());
            mysql.setMemory(v1Deployment.getSpec().getTemplate().getSpec().getContainers().get(0).getResources()
                    .getLimits().get("memory").toSuffixedString());
            mysql.setGmtCreate(v1Deployment.getMetadata().getCreationTimestamp().toDate());
            mysql.setVersion(v1Deployment.getMetadata().getLabels().get("version"));
            mysqls.add(mysql);
        }

        return mysqls;
    }

    @Override
    public Mysql updateMysql(Mysql mysql) throws ApiException, IllegalArgumentException,
            IllegalAccessException, CompilationFailedException, ClassNotFoundException, IOException {

        String image = "hub.centit.com/docker/mysql:" + mysql.getVersion();
 
        Map<String, Object> params = BeanUtils.convertToMap(mysql);
        params.put("namespace", namespace);
        params.put("image", image);
        
        String yaml = TemplateEngineUtils.binding(getClass().getClassLoader().getResource("templates/db/mysql-single-deployment.yaml").getFile(), params);
        
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        deploymentService.updateDeployment(namespace, objectMapper.readValue(yaml, V1Deployment.class));
        return mysql;
    }

    @Override
    public Mysql createDualNodeMysql(Mysql mysql) throws ApiException {
        // TODO: Auto-generated method stub
        return null;
    }
    
}
