package com.centit.kubernetes.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.kubernetes.client.openapi.models.V1Status;

public interface ServiceService {
    
    V1Service createService(String namespace, V1Service v1Service) throws ApiException;

    V1Status deleteService(String namespace, String name) throws ApiException;

    V1Service getService(String namespace, String name) throws ApiException;

    V1ServiceList listServices(String namespace) throws ApiException;
}
