package com.centit.kubernetes.service.impl;

import com.centit.kubernetes.service.ServiceService;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.kubernetes.client.openapi.models.V1Status;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    CoreV1Api corev1Api = new CoreV1Api();

    @Override
    public V1Service createService(String namespace, V1Service v1Service) throws ApiException {

        v1Service = corev1Api.createNamespacedService(namespace, v1Service, null, null, null);
        return v1Service;
    }

    @Override
    public V1Status deleteService(String namespace, String name) throws ApiException {
        V1Status v1Status = corev1Api.deleteNamespacedService(name, namespace, null, null, null, null, null, null);
        return v1Status;
    }

    @Override
    public V1Service getService(String namespace, String name) throws ApiException {
        V1Service v1Service = corev1Api.readNamespacedService(name, namespace, null, null, null);
        return v1Service;
    }

    @Override
    public V1ServiceList listServices(String namespace) throws ApiException {
        V1ServiceList v1ServiceList = corev1Api.listNamespacedService(namespace, null, null, null, null, null, null,
                null, null, null);
        return v1ServiceList;
    }

}
