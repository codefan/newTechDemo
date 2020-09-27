package com.centit.kubernetes.service.impl;

import com.centit.kubernetes.service.DeploymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
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
    public ApiResponse<V1Deployment> createDeployment(String namespace, V1Deployment v1Deployment) throws ApiException {
        ApiResponse<V1Deployment> apiResponse = appsV1Api.createNamespacedDeploymentWithHttpInfo(namespace, v1Deployment, null, null, null);
        return apiResponse;
    }

    @Override
    public ApiResponse<V1Status> deleteDeployment(String namespace, String name) throws ApiException {
        ApiResponse<V1Status> apiResponse = appsV1Api.deleteNamespacedDeploymentWithHttpInfo(name, namespace, null, null, null, null, null, null);
        return apiResponse;
    }

    @Override
    public ApiResponse<V1Deployment> getDeployment(String namespace, String name) throws ApiException {
        ApiResponse<V1Deployment> apiResponse = appsV1Api.readNamespacedDeploymentWithHttpInfo(name, namespace, null, null, null);

        return apiResponse;
    }

    @Override
    public ApiResponse<V1DeploymentList> listDeployment(String namespace) throws ApiException {
        ApiResponse<V1DeploymentList> apiResponse = appsV1Api.listNamespacedDeploymentWithHttpInfo(namespace, null, null, null, null, null, null, null, null, null);
        return apiResponse;
    }
    
    
}
