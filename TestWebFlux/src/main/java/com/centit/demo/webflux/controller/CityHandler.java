package com.centit.demo.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CityHandler {
    public Mono<ServerResponse> helloCity(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
            .header("Content-Type","text/plain; charset=utf-8")
            .body(BodyInserters.fromObject("Hello, " +
                request.queryParam("name").orElse("unknown") + "!"));
    }
}
