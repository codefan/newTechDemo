package com.centit.kubernetes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NFSVolumeSourceBuilder;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.openapi.models.V1PersistentVolume;
import io.kubernetes.client.openapi.models.V1PersistentVolumeBuilder;
import io.kubernetes.client.openapi.models.V1PersistentVolumeSpecBuilder;

@RestController
@RequestMapping("/persistentvolumes")
public class PersistentVolumeController {

    CoreV1Api coreV1api = new CoreV1Api();

    private final static String API_VERSION = "v1";
    private final static String KIND = "PersistentVolume";

    @GetMapping("")
    public ApiResponse getPersistentVolume() {

        return null;
    }

    @GetMapping("/{name}")
    public ApiResponse listPersistentVolume() {

        return null;
    }

    public ApiResponse deletePersistentVolume() {

        return null;
    }

    @PostMapping("")
    public ApiResponse<V1PersistentVolume> createPersistentVolume() throws ApiException {
        
        V1PersistentVolume v1PersistentVolume = new V1PersistentVolumeBuilder()
            .withApiVersion("v1")
            .withKind(KIND)
            .withMetadata(new V1ObjectMetaBuilder()
                .withName("")
                .withLabels(null)
                .build())
            .withSpec(new V1PersistentVolumeSpecBuilder()
                .withNfs(new V1NFSVolumeSourceBuilder()
                    .withPath("")
                    .withServer("")
                    .build())
                .withAccessModes("")
                .withCapacity(null)
                .withStorageClassName("")
                .build())
            .build();

        ApiResponse<V1PersistentVolume> apiResponse = coreV1api.createPersistentVolumeWithHttpInfo(v1PersistentVolume, null, null, null);
        return apiResponse;
    }
}
