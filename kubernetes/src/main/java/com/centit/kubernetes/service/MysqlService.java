package com.centit.kubernetes.service;

import java.io.IOException;
import java.util.List;

import com.centit.kubernetes.entity.Mysql;

import org.codehaus.groovy.control.CompilationFailedException;

import io.kubernetes.client.openapi.ApiException;

public interface MysqlService {
    
    public Mysql createMysql(Mysql mysql) throws Exception;
    public Mysql deleteMysql(String name) throws Exception;
    public Mysql getMysql(String name) throws Exception;
    public List<Mysql> listMysql() throws Exception;
    public Mysql updateMysql(Mysql mysql) throws ApiException, IllegalArgumentException, IllegalAccessException, CompilationFailedException, ClassNotFoundException, IOException;

    public Mysql createDualNodeMysql(Mysql mysql) throws ApiException;
}
