package com.centit.kubernetes.service.impl;

import com.centit.kubernetes.service.StatefulSetService;

import org.springframework.beans.factory.annotation.Autowired;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1StatefulSet;
import io.kubernetes.client.openapi.models.V1StatefulSetList;
import io.kubernetes.client.openapi.models.V1Status;

public class StatefulSetServiceImpl implements StatefulSetService {

    @Autowired
    AppsV1Api appsV1Api;

    @Override
    public V1StatefulSet createStatefulSet(String namespace, V1StatefulSet v1StatefulSet) throws ApiException {
        return appsV1Api.createNamespacedStatefulSet(namespace, v1StatefulSet, null, null, null);
    }

    @Override
    public V1Status deleteStatefulSet(String namespace, String name) throws ApiException {
        return appsV1Api.deleteNamespacedStatefulSet(name, namespace, null, null, null, null, null, null);
    }

    @Override
    public V1StatefulSet updateStatefulSet(String namespace, V1StatefulSet v1StatefulSet) throws ApiException {
        return appsV1Api.replaceNamespacedStatefulSet(v1StatefulSet.getMetadata().getName(), namespace, v1StatefulSet, null, null, null);
    }

    @Override
    public V1StatefulSet getStatefulSet(String namespace, String name) throws ApiException {
        
        return appsV1Api.readNamespacedStatefulSet(name, namespace, null, null, null);
    }

    @Override
    public V1StatefulSetList listStatefulSet(String namespace) throws ApiException {
        return appsV1Api.listNamespacedStatefulSet(namespace, null, null, null, null, null, null, null, null, null);
    }
}
