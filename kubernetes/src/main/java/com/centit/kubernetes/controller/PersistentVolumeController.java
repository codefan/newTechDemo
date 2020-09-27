package com.centit.kubernetes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PersistentVolume;

@RestController
@RequestMapping("/persistentvolumes")
public class PersistentVolumeController {

    CoreV1Api coreV1api = new CoreV1Api();

    @GetMapping("")
    public ApiResponse<V1PersistentVolume> getPersistentVolume() {

        return null;
    }

    @GetMapping("/{name}")
    public ApiResponse<V1PersistentVolume> listPersistentVolume() {

        return null;
    }

    public ApiResponse<V1PersistentVolume> deletePersistentVolume() {

        return null;
    }

    @PostMapping("")
    public ApiResponse<V1PersistentVolume> createPersistentVolume() throws ApiException {
        

        return null;
    }
}
