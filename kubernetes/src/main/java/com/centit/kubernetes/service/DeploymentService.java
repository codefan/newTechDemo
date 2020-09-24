package com.centit.kubernetes.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1Status;

public interface DeploymentService {

    ApiResponse<V1Deployment> createDeployment(String namespace, V1Deployment v1Deployment) throws ApiException;

    ApiResponse<V1Status> deleteDeployment(String namespace, String name) throws ApiException;

    ApiResponse<V1Deployment> getDeployment(String namespace, String name) throws ApiException;

    ApiResponse<V1DeploymentList> listDeployment(String namespace) throws ApiException;
}
