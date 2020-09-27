package com.centit.kubernetes.service;

import java.util.List;

import com.centit.kubernetes.entity.Mysql;

public interface MysqlService {
    
    public Mysql createMysql(String namespace, Mysql mysql) throws Exception;
    public Mysql deleteMysql(String namespace, String name) throws Exception;
    public Mysql getMysql(String namespace, String name) throws Exception;
    public List<Mysql> listMysql(String namespace) throws Exception;
}
