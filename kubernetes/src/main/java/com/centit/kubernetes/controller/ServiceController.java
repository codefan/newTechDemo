package com.centit.kubernetes.controller;

import com.centit.kubernetes.service.ServiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.models.V1ServiceList;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @GetMapping("")
    public V1ServiceList listServices(String namespace) throws ApiException {
        V1ServiceList v1ServiceList = serviceService.listServices(namespace);
        return v1ServiceList;
    }
}
