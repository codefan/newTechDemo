package com.centit.kubernetes.constant;

/**
 * K8S常用API描述文件API版本与类型
 */
public enum KubeMetaEnum {
    
    DEPLOYMENT("apps/v1", "Deployment"),
    STATEFULSET("v1", "Statefulset"),
    SERVICE("v1", "Service");


    String apiVersion;
    String kind;

    KubeMetaEnum(String apiVersion, String kind){
        this.apiVersion = apiVersion;
        this.kind = kind;
    }

    public String getApiVersion(){
        return apiVersion;
    }

    public String getKind(){
        return kind;
    }
}
