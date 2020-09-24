package com.centit.kubernetes.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ContainerBuilder;
import io.kubernetes.client.openapi.models.V1ContainerPortBuilder;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentBuilder;
import io.kubernetes.client.openapi.models.V1DeploymentSpecBuilder;
import io.kubernetes.client.openapi.models.V1LabelSelectorBuilder;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1PodSpecBuilder;
import io.kubernetes.client.openapi.models.V1PodTemplateSpecBuilder;
import io.kubernetes.client.openapi.models.V1ResourceRequirementsBuilder;
import io.kubernetes.client.openapi.models.V1StatefulSetBuilder;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

@RestController
@RequestMapping("/api")
public class KubeApiController {

    @GetMapping("/pod")
    public V1PodList getRestApi() throws FileNotFoundException, IOException, ApiException {

        String kubeConfigPath = getClass().getClassLoader().getResource(".kube/config").getFile();

        ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);

        return list;
    }

    @GetMapping("/deployment")
    public V1Deployment createDeployment() throws ApiException {

        ApiClient apiclient = Configuration.getDefaultApiClient();
        Map labels = new HashMap<String,String>();
        labels.put("name", "nginx");
        Map limits = new HashMap<String, String>();
        limits.put("cpu", "500m");
        limits.put("memory", "128Mi");
        AppsV1Api appsV1Api = new AppsV1Api(apiclient);


        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        v1ObjectMeta.setName("nginx");

        V1Deployment v1Deployment = new V1DeploymentBuilder()
            .withApiVersion("apps/v1")
            .withKind("Deployment")
            .withMetadata(new V1ObjectMetaBuilder()
                .withName("nginx")
                .build())
            .withSpec(new V1DeploymentSpecBuilder()
                .withSelector(new V1LabelSelectorBuilder()
                    .withMatchLabels(labels)
                    .build())
                .withTemplate(new V1PodTemplateSpecBuilder()
                    .withMetadata(new V1ObjectMetaBuilder()
                        .withLabels(labels)
                        .build())
                    .withSpec(new V1PodSpecBuilder()
                        .withContainers(new V1ContainerBuilder()
                            .withName("nginx")
                            .withImage("nginx")
                            .withPorts(new V1ContainerPortBuilder()
                                .withContainerPort(80)
                                .build())
                            .withResources(new V1ResourceRequirementsBuilder()
                                .withLimits(limits)
                                .build())
                            .build())
                    .build())
                .build())
            .build())
        .build();

        V1Deployment deployment = appsV1Api.createNamespacedDeployment("default", v1Deployment, null, null, null);
        
        //异步创建并执行回调函数
        //appsV1Api.createNamespacedDeploymentAsync(namespace, body, pretty, dryRun, fieldManager, _callback)
        //同步创建并执行回调函数
        //appsV1Api.createNamespacedDeploymentCall(namespace, body, pretty, dryRun, fieldManager, _callback)
        //创建并返回http状态码
        //appsV1Api.createNamespacedDeploymentWithHttpInfo(namespace, body, pretty, dryRun, fieldManager)
        return deployment;
    }

    @DeleteMapping("/deployment")
    public ApiResponse<V1Status> deleteDeployment() throws ApiException {

        ApiClient apiclient = Configuration.getDefaultApiClient();
        AppsV1Api appsV1Api = new AppsV1Api(apiclient);
        ApiResponse<V1Status> apiResponse = appsV1Api.deleteNamespacedDeploymentWithHttpInfo("nginx", "default", null, null, null, null, null, null);
        return apiResponse;
    }

    @GetMapping("/statefulset")
    public ApiResponse<V1Status> createStatefulset(){
        ApiClient apiclient = Configuration.getDefaultApiClient();
        AppsV1Api appsV1Api = new AppsV1Api(apiclient);
        
        new V1StatefulSetBuilder().buildMetadata().name("mysql");
        //new V1StatefulSetFluentImpl<>().buildSpec().serviceName("").selector(new V1LabelSelectorFluentImpl().build)
        return null;
    }
}