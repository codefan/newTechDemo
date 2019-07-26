package com.centit.demo.netty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestCaseController {

    public String getOptId(){
        return "testCtrl";
    }

    @GetMapping("/sayhello/{userName}")
    public String sayHello(@PathVariable String userName){
        return "hello "+userName+" !";
    }

}
