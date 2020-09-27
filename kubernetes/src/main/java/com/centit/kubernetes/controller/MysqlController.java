package com.centit.kubernetes.controller;

import java.util.List;
import java.util.Map;

import com.centit.kubernetes.entity.Mysql;
import com.centit.kubernetes.service.MysqlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.models.V1Deployment;

@RestController
@RequestMapping("/db/mysql")
public class MysqlController {
    
    String namespace = "test";

    @Autowired
    MysqlService mysqlService;

    @PostMapping("")
    public ResponseEntity<Mysql> createMysql(Mysql mysql) throws Exception {
        
        mysql = mysqlService.createMysql(namespace, mysql);
        
        return ResponseEntity.ok(mysql);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Mysql> deleteMysql(@PathVariable String name) throws Exception {

        Mysql mysql = mysqlService.deleteMysql(namespace, name);
        return ResponseEntity.ok(mysql);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Mysql> getMysql(@PathVariable String name) throws Exception {

        Mysql mysql = mysqlService.getMysql(namespace, name);
        return ResponseEntity.ok(mysql);
    }

    @GetMapping("")
    public ResponseEntity<List<Mysql>> listMysql() throws Exception {

        List<Mysql> mysqls = mysqlService.listMysql(namespace);

        return ResponseEntity.ok(mysqls);
    }
}
