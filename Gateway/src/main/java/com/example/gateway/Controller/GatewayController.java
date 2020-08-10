package com.example.gateway.Controller;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class GatewayController {

    @Bean
    public RouteLocator GatewayController(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes().route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri("localhost:7070"))//http://httpbin.org:80
                .build();
    }

    @GetMapping("/get")
    public String GetController(ServletWebRequest servletWebRequest) {

        return servletWebRequest.getHeader("Hello");
    }
}
