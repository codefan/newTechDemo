package com.centit.demo.webflux;

import com.centit.demo.webflux.controller.CityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityRouter {

    @Bean
    public RouterFunction<ServerResponse> routeCity(
        @Autowired CityHandler cityHandler) {

        return RouterFunctions
            .route(RequestPredicates.GET("/hello")
                    .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                cityHandler::helloCity)
            //添加中文过滤器； 这个是行不通的
            /*.filter( (serverRequest, nextHandlerFunction) ->
                nextHandlerFunction.handle(serverRequest)*//*.flatMap(
                    s -> {
                        ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                            //.header("Content-Type","text/plain; charset=utf-8")
                            .body(s)
                        s.headers().add("Content-Type", "text/plain; charset=utf-8");
                        return  Mono.just(s);
                    }*//*
                )*/;
    }
}
