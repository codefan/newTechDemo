package com.centit.kubernetes.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

@org.springframework.context.annotation.Configuration
public class KubernetesConfig {

    @PostConstruct
    public void initConfig() throws FileNotFoundException, IOException {

        String kubeConfigPath = getClass().getClassLoader().getResource(".kube/config").getFile();
        ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);
    }

    @Bean
    public AppsV1Api getAppsV1Api(){
        return new AppsV1Api();
    }
}
