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
                cityHandler::helloCity);
    }

}
