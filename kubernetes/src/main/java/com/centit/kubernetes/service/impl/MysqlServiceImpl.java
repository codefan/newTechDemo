package com.centit.kubernetes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.centit.kubernetes.constant.KubeMetaEnum;
import com.centit.kubernetes.entity.Mysql;
import com.centit.kubernetes.service.DeploymentService;
import com.centit.kubernetes.service.MysqlService;
import com.centit.kubernetes.service.ServiceService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1ContainerBuilder;
import io.kubernetes.client.openapi.models.V1ContainerPortBuilder;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentBuilder;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1DeploymentSpecBuilder;
import io.kubernetes.client.openapi.models.V1DeploymentStrategyBuilder;
import io.kubernetes.client.openapi.models.V1EnvVar;
import io.kubernetes.client.openapi.models.V1EnvVarBuilder;
import io.kubernetes.client.openapi.models.V1HostPathVolumeSourceBuilder;
import io.kubernetes.client.openapi.models.V1LabelSelectorBuilder;
import io.kubernetes.client.openapi.models.V1NFSVolumeSourceBuilder;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.openapi.models.V1PodSpecBuilder;
import io.kubernetes.client.openapi.models.V1PodTemplateSpecBuilder;
import io.kubernetes.client.openapi.models.V1ResourceRequirementsBuilder;
import io.kubernetes.client.openapi.models.V1RollingUpdateDeploymentBuilder;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceBuilder;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.kubernetes.client.openapi.models.V1ServicePortBuilder;
import io.kubernetes.client.openapi.models.V1ServiceSpecBuilder;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.openapi.models.V1Volume;
import io.kubernetes.client.openapi.models.V1VolumeBuilder;
import io.kubernetes.client.openapi.models.V1VolumeMount;
import io.kubernetes.client.openapi.models.V1VolumeMountBuilder;

@Service
public class MysqlServiceImpl implements MysqlService {


    @Autowired
    AppsV1Api appsV1Api;

    @Autowired
    ServiceService serviceService;

    @Autowired
    DeploymentService deploymentService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 创建deployment成功后创建svc，并将初始密码与实例ID与随机端口返回
     * 
     * @throws Exception
     */
    @Override
    public Mysql createMysql(String namespace, V1Deployment v1Deployment) throws Exception {
        
        //满足DNS-1123命名规则
        String chars = "0123456789qwertyuiopasdfghjklzxcvbnm";
        //metadata
        String deploymentName = "mysql" + RandomStringUtils.random(8, chars);

        //spec.selector && spec.template.metadata
        Map<String, String> labels = new HashMap<String, String>();
        labels.put("name", deploymentName);
        labels.put("type", "datebase");
        labels.put("version", "5.7.31");

        //spec.template.spec.containers
        String containerName = deploymentName;
        String imageName = "hub.centit.com/docker/mysql:5.7.31";

        //spec.template.spec.containers.resources
        Map<String, Quantity> limits = new HashMap<>();
        limits.put("memory", new Quantity("256Mi"));
        limits.put("cpu", new Quantity("500m"));

        //spec.template.spec.containers.ports
        int containerPort = 3306;

        //spec.template.spec.containers.volumeMounts
        List<V1VolumeMount> v1VolumeMounts = new ArrayList<>();
        v1VolumeMounts.add(new V1VolumeMountBuilder()
            .withName("nfs")
            .withMountPath("/var/lib/mysql")
            .withSubPath("database/" + deploymentName + "/data")
            .build());
        v1VolumeMounts.add(new V1VolumeMountBuilder()
            .withName("nfs")
            .withMountPath("/etc/mysql")
            .withSubPath("database/" + deploymentName + "/conf")
            .build());
        v1VolumeMounts.add(new V1VolumeMountBuilder()
            .withName("nfs")
            .withMountPath("/var/log/mysql")
            .withSubPath("database/" + deploymentName + "/logs")
            .build());
        v1VolumeMounts.add(new V1VolumeMountBuilder()
            .withName("time")
            .withMountPath("/etc/localtime")
            .build());

        List<V1EnvVar> v1EnvVars = new ArrayList<>();
        v1EnvVars.add(new V1EnvVarBuilder()
            .withName("MYSQL_ROOT_PASSWORD")
            .withValue("root")
            .build());

        //spec.tempalte.spec.volumes
        List<V1Volume> v1Volumes = new ArrayList<>();
        v1Volumes.add(new V1VolumeBuilder()
            .withName("time")
            .withHostPath(new V1HostPathVolumeSourceBuilder()
                .withPath("/etc/localtime")
                .build())
            .build());
        v1Volumes.add(new V1VolumeBuilder()
            .withName("nfs")
            .withNfs(new V1NFSVolumeSourceBuilder()
                .withPath("/nfsdata")
                .withServer("192.168.128.71")
                .build())
            .build());

        v1Deployment = new V1DeploymentBuilder(v1Deployment)
            .withApiVersion(KubeMetaEnum.DEPLOYMENT.getApiVersion())
            .withKind(KubeMetaEnum.DEPLOYMENT.getKind())
            .withMetadata(new V1ObjectMetaBuilder()
                .withName(deploymentName)
                .build())
            .withSpec(new V1DeploymentSpecBuilder()
                .withSelector(new V1LabelSelectorBuilder()
                    .withMatchLabels(labels)
                    .build())
                .withStrategy(new V1DeploymentStrategyBuilder()
                    .withRollingUpdate(new V1RollingUpdateDeploymentBuilder()
                        .withMaxSurge(new IntOrString("50%"))
                        .build())
                    .build())
                .withTemplate(new V1PodTemplateSpecBuilder()
                    .withMetadata(new V1ObjectMetaBuilder()
                        .withLabels(labels)
                        .build())
                    .withSpec(new V1PodSpecBuilder()
                        .withContainers(new V1ContainerBuilder()
                            .withName(containerName)
                            .withImage(imageName)
                            .withResources(new V1ResourceRequirementsBuilder()
                                .withLimits(limits)
                                .build())
                            .withPorts(new V1ContainerPortBuilder()
                                .withContainerPort(containerPort)
                                .build())
                            .withVolumeMounts(v1VolumeMounts)
                            .withEnv(v1EnvVars)
                            .build())
                        .withVolumes(v1Volumes)
                        .build())
                    .build())
                .build())
            .build();

        ApiResponse<V1Deployment> apiResponse = deploymentService.createDeployment(namespace, v1Deployment);

        logger.info(apiResponse.toString());
        
        if(apiResponse.getStatusCode() < 200 && apiResponse.getStatusCode() >= 300){
            throw new Exception("deployment创建失败: " + apiResponse);
        }

        /**
         * 创建svc
         */
        String serviceName = deploymentName + "-svc";
        int nodePort = 0;

        V1Service v1Service = new V1ServiceBuilder()
            .withApiVersion(KubeMetaEnum.SERVICE.getApiVersion())
            .withKind(KubeMetaEnum.SERVICE.getKind())
            .withMetadata(new V1ObjectMetaBuilder()
                .withName(serviceName)
                .withNamespace(namespace)
                .build())
            .withSpec(new V1ServiceSpecBuilder()
                .withSelector(labels)
                .withPorts(new V1ServicePortBuilder()
                    .withPort(3306) //通过kube-apiserver的node port range随机生成端口
                    .build())
                .withType("NodePort")
                .build())
            .build();
        ApiResponse<V1Service> svcApiResponse = serviceService.createService(namespace, v1Service);
        
        if(svcApiResponse.getStatusCode() < 200 && svcApiResponse.getStatusCode() >= 300){
            throw new Exception("service创建失败: " + apiResponse);
        }

        // TO-DO 单实例应用只有默认只有1组port,当有多组port时，以下方法需要更改
        nodePort = svcApiResponse.getData().getSpec().getPorts().get(0).getNodePort(); 
        svcApiResponse.getData().getStatus();
        Mysql mysql = new Mysql();
        mysql.setName(deploymentName);
        mysql.setPassword("root");
        mysql.setPort(nodePort);
        return mysql;
    }

    @Override
    public Mysql deleteMysql(String namespace, String name) throws Exception {

        deploymentService.deleteDeployment(namespace, name);

        serviceService.deleteService(namespace, name + "-svc");

        Mysql mysql = new Mysql();
        mysql.setName(name);

        return mysql;
    }

    @Override
    public Mysql getMysql(String namespace, String name) throws ApiException {

        ApiResponse<V1Deployment> deploymentApiResponse = deploymentService.getDeployment(namespace, name);
        ApiResponse<V1Service> svcApiResponse = serviceService.getService(namespace, name + "-svc");

        Mysql mysql = new Mysql(name, svcApiResponse.getData().getSpec().getPorts().get(0).getNodePort(), "root");
        return mysql;
    }

    @Override
    public List<Mysql> listMysql(String namespace) throws ApiException {

        ApiResponse<V1ServiceList> apiResponse = serviceService.listServices(namespace);
        ApiResponse<V1DeploymentList> deploymentApiResponse = deploymentService.listDeployment(namespace);

        List<V1Service> v1Services = apiResponse.getData().getItems();
        List<V1Deployment> v1Deployments = deploymentApiResponse.getData().getItems();
        List<Mysql> mysqls = new ArrayList<Mysql>();

        for (V1Deployment v1Deployment : v1Deployments) {
            Mysql mysql = new Mysql();
            mysql.setName(v1Deployment.getMetadata().getName()); 
            for (V1Service v1Service : v1Services) {
                mysql.setPort(v1Service.getSpec().getPorts().get(0).getNodePort());
                mysql.setPassword("root");
            }
            mysqls.add(mysql);
        }
        
        return mysqls;
    }
    
}
