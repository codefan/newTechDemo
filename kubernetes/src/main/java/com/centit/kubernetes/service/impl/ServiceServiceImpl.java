package com.centit.kubernetes.service.impl;

import com.centit.kubernetes.service.ServiceService;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.kubernetes.client.openapi.models.V1Status;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    CoreV1Api corev1Api = new CoreV1Api();

    @Override
    public ApiResponse<V1Service> createService(String namespace, V1Service v1Service) throws ApiException {

        ApiResponse<V1Service> apiResponse = corev1Api.createNamespacedServiceWithHttpInfo(namespace, v1Service, null,
                null, null);
        return apiResponse;
    }

    @Override
    public ApiResponse<V1Status> deleteService(String namespace, String name) throws ApiException {
        ApiResponse<V1Status> apiResponse = corev1Api.deleteNamespacedServiceWithHttpInfo(name, namespace, null, null,
                null, null, null, null);
        return apiResponse;
    }

    @Override
    public ApiResponse<V1Service> getService(String namespace, String name) throws ApiException {
        ApiResponse<V1Service> apiResponse = corev1Api.readNamespacedServiceWithHttpInfo(name, namespace, null, null, null);
        return apiResponse;
    }

    @Override
    public ApiResponse<V1ServiceList> listServices(String namespace) throws ApiException {
        ApiResponse<V1ServiceList> apiResponse = corev1Api.listNamespacedServiceWithHttpInfo(namespace,null, null, null, null, null, null, null, null, null);
        return apiResponse;
    }

}
