package com.centit.kubernetes.controller;

import com.centit.kubernetes.service.DeploymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1Status;

@RestController
@RequestMapping("/deployment")
public class DeploymentController {

    String namespace = "test";

    @Autowired
    DeploymentService deploymentService;

    @GetMapping("")
    public V1DeploymentList listDeployment() throws ApiException {
        V1DeploymentList v1DeploymentList = deploymentService.listDeployment(namespace);
        return v1DeploymentList;
    }

    @GetMapping("/{name}")
    public V1Deployment getDeployment(@PathVariable String name) throws ApiException {

        V1Deployment v1Deployment = deploymentService.getDeployment(namespace, name);
        return v1Deployment;
    }

    @DeleteMapping("/{name}")
    public V1Status deleteDeployment(@PathVariable String name) throws ApiException {
        V1Status v1Status = deploymentService.deleteDeployment(namespace, name);
        return v1Status;
    }

    @PostMapping("")
    public ApiResponse<V1Deployment> createDeployment() throws ApiException {
        
        return null;
    }
}
