package com.centit.kubernetes.service;

import java.util.List;

import com.centit.kubernetes.entity.Mysql;

import io.kubernetes.client.openapi.models.V1Deployment;

public interface MysqlService {
    
    public Mysql createMysql(String namespace, V1Deployment V1Deployment) throws Exception;
    public Mysql deleteMysql(String namespace, String name) throws Exception;
    public Mysql getMysql(String namespace, String name) throws Exception;
    public List<Mysql> listMysql(String namespace) throws Exception;
}
