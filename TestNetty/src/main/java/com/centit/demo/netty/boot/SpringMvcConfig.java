package com.centit.demo.netty.boot;

import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zou_wy on 2017/3/29.
 */
@ComponentScan(basePackages = {"com.centit.demo.netty.controller"},
        includeFilters = {@ComponentScan.Filter(value= org.springframework.stereotype.Controller.class)},
        useDefaultFilters = false)
public class SpringMvcConfig {

}
