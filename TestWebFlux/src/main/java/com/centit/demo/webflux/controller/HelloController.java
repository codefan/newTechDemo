package com.centit.demo.webflux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/say/{ownerId}")
    @ResponseBody
    public String findPet(@PathVariable String ownerId) {

        return ownerId +" hello =>" + ownerId;
    }
}
