package com.centit.kubernetes.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1StatefulSet;
import io.kubernetes.client.openapi.models.V1StatefulSetList;
import io.kubernetes.client.openapi.models.V1Status;

public interface StatefulSetService {

    public V1StatefulSet createStatefulSet(String namespace, V1StatefulSet v1StatefulSet) throws ApiException;

    public V1Status deleteStatefulSet(String namespace, String name) throws ApiException;

    public V1StatefulSet updateStatefulSet(String namespace, V1StatefulSet v1StatefulSet) throws ApiException;

    public V1StatefulSet getStatefulSet(String namespace, String name) throws ApiException;

    public V1StatefulSetList listStatefulSet(String namespace) throws ApiException;
}
