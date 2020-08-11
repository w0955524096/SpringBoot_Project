package com.example.gateway.Controller;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class GatewayController {

    @Bean
    public RouteLocator GatewayController(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes().route(p -> p
                .method(HttpMethod.GET)
                .and()
                .path("/SpringService")
                .filters(f -> f.addRequestHeader("H", "Z"))
                .uri("http://localhost:6663"))//https://console-nlu.lingtelli.com
                .build();
    }

    @GetMapping("/cl/{id}")
    public String GetController(ServletWebRequest servletWebRequest, @PathVariable("id") String id ) {

        return servletWebRequest.getHeader("H")+id;
    }
}
