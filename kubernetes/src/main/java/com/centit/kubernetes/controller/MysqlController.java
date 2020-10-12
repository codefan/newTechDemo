package com.centit.kubernetes.controller;

import java.io.IOException;
import java.util.List;

import com.centit.kubernetes.entity.Mysql;
import com.centit.kubernetes.service.MysqlService;
import com.centit.kubernetes.utils.RandomUtils;
import com.fasterxml.jackson.annotation.JsonView;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/db/mysql")
@Api("mysql实例")
public class MysqlController {

    @Autowired
    MysqlService mysqlService;

    @ApiOperation(value = "创建mysql单机实例")
    @PostMapping("")
    public ResponseEntity<Mysql> createMysql(Mysql mysql) throws Exception {

        mysql.setName("mysql" + RandomUtils.randomDNS1123());
        mysql = mysqlService.createMysql(mysql);
        return ResponseEntity.status(HttpStatus.CREATED).body(mysql);
    }

    @ApiOperation(value = "删除mysql单机实例")
    @DeleteMapping("/{name}")
    public ResponseEntity<Mysql> deleteMysql(@PathVariable String name) throws Exception {

        Mysql mysql = mysqlService.deleteMysql(name);
        return ResponseEntity.ok(mysql);
    }

    @ApiOperation(value = "根据名称获取mysql单机实例")
    @GetMapping("/{name}")
    @JsonView(Mysql.GetView.class)
    public ResponseEntity<Mysql> getMysql(@PathVariable String name) throws Exception {

        Mysql mysql = mysqlService.getMysql(name);
        return ResponseEntity.ok(mysql);
    }

    @ApiOperation(value = "获取所有mysql单机实例")
    @GetMapping("")
    public ResponseEntity<List<Mysql>> listMysql() throws Exception {

        List<Mysql> mysqls = mysqlService.listMysql();

        return ResponseEntity.ok(mysqls);
    }

    @ApiOperation("更新mysql实例")
    @PutMapping("")
    public ResponseEntity<Mysql> updateMysql(Mysql mysql) throws CompilationFailedException, IllegalArgumentException,
            IllegalAccessException, ClassNotFoundException, ApiException, IOException {
        mysql = mysqlService.updateMysql(mysql);
        return ResponseEntity.ok(mysql);
    }
}
