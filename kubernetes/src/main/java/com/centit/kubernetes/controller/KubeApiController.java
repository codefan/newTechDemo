package com.centit.kubernetes.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.centit.kubernetes.config.KubernetesProperties;
import com.centit.kubernetes.service.DeploymentService;
import com.centit.kubernetes.utils.RandomUtils;
import com.centit.kubernetes.utils.TemplateEngineUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.models.V1Deployment;

@RestController
@RequestMapping("/api")
public class KubeApiController {

    @Autowired
    DeploymentService deploymentService;

    @Autowired
    KubernetesProperties kubernetesProperties;

    @GetMapping("/test")
    public V1Deployment getRestApi() throws IOException, ClassNotFoundException {
        
        Map<String, Object> params = new HashMap<>();
        String name = "mysql" + RandomUtils.randomDNS1123();
        params.put("name", name);
        params.put("image", "hub.centit.com/library/mysql:5.7.31");
        params.put("version", "5.7.31");
        params.put("memory", "256Mi");
        params.put("cpu", "500m");
        params.put("MYSQL_ROOT_PASSWORD", "root");
        String yaml = TemplateEngineUtils.binding(getClass().getClassLoader().getResource("templates/db/mysql-single-deployment.yaml").getFile(), params);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        V1Deployment v1Deployment = objectMapper.readValue(yaml, V1Deployment.class);
        // String yaml = TemplateEngineUtils.binding(getClass().getClassLoader().getResource("templates/db/nginx.yaml").getFile(), params);
        // V1Pod v1Pod = objectMapper.readValue(yaml, V1Pod.class);
        System.out.println(kubernetesProperties.getNamespace());
        return v1Deployment;
    }
}