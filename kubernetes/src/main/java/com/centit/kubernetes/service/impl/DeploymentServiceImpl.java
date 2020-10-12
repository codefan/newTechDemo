package com.centit.kubernetes.service.impl;

import com.centit.kubernetes.service.DeploymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1Status;

@Service
public class DeploymentServiceImpl implements DeploymentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppsV1Api appsV1Api;

    @Override
    public V1Deployment createDeployment(String namespace, V1Deployment v1Deployment) throws ApiException {
        v1Deployment = appsV1Api.createNamespacedDeployment(namespace, v1Deployment, null, null, null);
        return v1Deployment;
    }

    @Override
    public V1Status deleteDeployment(String namespace, String name) throws ApiException {
        V1Status v1Status = appsV1Api.deleteNamespacedDeployment(name, namespace, null, null, null, null, null, null);
        return v1Status;
    }

    @Override
    public V1Deployment getDeployment(String namespace, String name) throws ApiException {
        V1Deployment v1Deployment = appsV1Api.readNamespacedDeployment(name, namespace, null, null, null);

        return v1Deployment;
    }

    @Override
    public V1DeploymentList listDeployment(String namespace) throws ApiException {
        V1DeploymentList v1DeploymentList = appsV1Api.listNamespacedDeployment(namespace, null, null, null, null, null, null, null, null, null);
        return v1DeploymentList;
    }

    @Override
    public V1Deployment updateDeployment(String namespace, V1Deployment v1Deployment) throws ApiException {
        v1Deployment = appsV1Api.replaceNamespacedDeployment(v1Deployment.getMetadata().getName(), namespace,v1Deployment, null, null, null);
        return v1Deployment;
    }
    
    
}
