package com.centit.kubernetes.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1ContainerBuilder;
import io.kubernetes.client.openapi.models.V1ContainerPortBuilder;
import io.kubernetes.client.openapi.models.V1EnvVar;
import io.kubernetes.client.openapi.models.V1LabelSelectorBuilder;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.openapi.models.V1PodSpecBuilder;
import io.kubernetes.client.openapi.models.V1PodTemplateSpecBuilder;
import io.kubernetes.client.openapi.models.V1ResourceRequirementsBuilder;
import io.kubernetes.client.openapi.models.V1StatefulSet;
import io.kubernetes.client.openapi.models.V1StatefulSetBuilder;
import io.kubernetes.client.openapi.models.V1StatefulSetSpecBuilder;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.openapi.models.V1VolumeMount;

@RestController
@RequestMapping("/statefulset")
public class StatefulsetController {

    AppsV1Api appsV1Api = new AppsV1Api();

    private final static String API_VERSION = "apps/v1";
    private final static String KIND = "statefulset";

    @GetMapping("/namespaces/{namespace}/{statefulset}")
    public ApiResponse getStatefulset(@PathVariable String statefulset) {

        return null;
    }

    @GetMapping("")
    public ApiResponse listStatefulset() {

        return null;
    }

    @PostMapping("/namespaces/{namespace}/statefulsets")
    public ApiResponse<V1StatefulSet> createStatefulset(@PathVariable String namespace) throws ApiException {

        String image = "hub.centit.com/mysql:5.7.31";
        String containerName = null;
        String name = "mysql" + UUID.randomUUID();

        V1StatefulSet v1StatefulSet = new V1StatefulSetBuilder()
            .withApiVersion(API_VERSION)
            .withKind(KIND)
            .withMetadata(new V1ObjectMetaBuilder()
                .withName("")
                .build())
            .withSpec(new V1StatefulSetSpecBuilder()
                .withServiceName("")
                .withSelector(new V1LabelSelectorBuilder()
                    .withMatchLabels(null)
                    .build())
                .withTemplate(new V1PodTemplateSpecBuilder()
                    .withMetadata(new V1ObjectMetaBuilder()
                        .withLabels(null)
                        .build())
                    .withSpec(new V1PodSpecBuilder()
                        .withContainers(new V1ContainerBuilder()
                            .withName(containerName)
                            .withImage(image)
                            .withResources(new V1ResourceRequirementsBuilder()
                                .withLimits(null)
                                .build())
                            .withPorts(new V1ContainerPortBuilder()
                                .withContainerPort(3306)
                                .build())
                            .withEnv(new ArrayList<V1EnvVar>())
                            .withVolumeMounts(new ArrayList<V1VolumeMount>())
                            .build())
                        .withVolumes(new ArrayList<>())
                        .build())
                    .build())
                .build())
            .build();

        ApiResponse<V1StatefulSet> apiResponse = appsV1Api.createNamespacedStatefulSetWithHttpInfo(namespace, v1StatefulSet, null, null, null);

        return apiResponse;
    }

    @DeleteMapping("/namespaces/{namespace}/statefulsets/{name}")
    public ApiResponse<V1Status> deleteStatefulset(@PathVariable String namespace, @PathVariable String name)
            throws ApiException {
        
        ApiResponse<V1Status> apiResponse = appsV1Api.deleteNamespacedStatefulSetWithHttpInfo(name, namespace, null, null, null, null, null, null);
        return apiResponse;
    }
}
