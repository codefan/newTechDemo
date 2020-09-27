package com.centit.kubernetes.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;

@org.springframework.context.annotation.Configuration
public class KubernetesConfig {

    @Autowired
    KubernetesProperties KubernetesProperties;

    @PostConstruct
    public void initConfig() throws FileNotFoundException, IOException {

        // String kubeConfigPath = getClass().getClassLoader().getResource(".kube/config").getFile();
        // ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        // Configuration.setDefaultApiClient(client);

        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.setBasePath(KubernetesProperties.getConfig().getUrl());
        clientBuilder.setAuthentication(new AccessTokenAuthentication(KubernetesProperties.getConfig().getToken()));
       
        clientBuilder.setCertificateAuthority(Base64.decodeBase64(KubernetesProperties.getConfig().getCa()));
        
        
        ApiClient client = clientBuilder.build();

        Configuration.setDefaultApiClient(client);
    }

    @Bean
    public AppsV1Api getAppsV1Api(){
        return new AppsV1Api();
    }

    @Bean
    public KubernetesProperties getKubernetesProperties(){
        return new KubernetesProperties();
    }
}
