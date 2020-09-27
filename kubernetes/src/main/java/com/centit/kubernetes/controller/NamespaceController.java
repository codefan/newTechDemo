package com.centit.kubernetes.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceBuilder;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.openapi.models.V1Status;

@RestController
@RequestMapping("/namespace")
public class NamespaceController {

    private final static String API_VERSION = "v1";
    private final static String KIND = "Namespace";

    @GetMapping("")
    public ApiResponse<V1NamespaceList> listNamespace() throws ApiException {

        CoreV1Api coreV1Api = new CoreV1Api();
        ApiResponse<V1NamespaceList> apiResponse = coreV1Api.listNamespaceWithHttpInfo(null, null, null, null, null, null, null, null, null);
        return apiResponse;
    }

    @GetMapping("/{namespace}")
    public ApiResponse<V1Namespace> getNamespace(@PathVariable String namespace) throws ApiException {
        CoreV1Api coreV1Api = new CoreV1Api();
        ApiResponse<V1Namespace> apiResponse = coreV1Api.readNamespaceWithHttpInfo(namespace, null, null, null);

        return apiResponse;
    }

    @DeleteMapping("/{namespace}")
    public ApiResponse<V1Status> deleteNamespace(@PathVariable String namespace) throws ApiException {
        CoreV1Api coreV1Api = new CoreV1Api();
        ApiResponse<V1Status> apiResponse = coreV1Api.deleteNamespaceWithHttpInfo(namespace, null, null, null, null, null, null);

        return apiResponse;
    }

    @PostMapping("/{namespace}")
    public ApiResponse<V1Namespace> createNamespace(@PathVariable String namespace) throws ApiException {

        CoreV1Api coreV1Api = new CoreV1Api();
        V1Namespace v1Namespace = new V1NamespaceBuilder()
            .withApiVersion(API_VERSION)
            .withKind(KIND)
            .withMetadata(new V1ObjectMetaBuilder()
                .withName(namespace)
                .build())
            .build();

        ApiResponse<V1Namespace> apiResponse = coreV1Api.createNamespaceWithHttpInfo(v1Namespace, null, null, null);

        return apiResponse;
    }
}
