package com.centit.demo.netty.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by codefan on 17-7-18.
 */
@Configuration
public class ServiceBeanConfig {

    @Bean
    public DispatcherServlet servlet(){
        AnnotationConfigWebApplicationContext context
            = new AnnotationConfigWebApplicationContext();
        context.register(SpringMvcConfig.class);
        return new DispatcherServlet(context);

    }

}
