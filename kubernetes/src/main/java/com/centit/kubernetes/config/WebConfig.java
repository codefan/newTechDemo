package com.centit.kubernetes.config;

import java.util.List;

import com.google.gson.Gson;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    private Gson gson = new Gson();

    // @Override
    // public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //     GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    //     gsonHttpMessageConverter.setGson(gson);
    //     converters.add(gsonHttpMessageConverter);
        
    // }
}
