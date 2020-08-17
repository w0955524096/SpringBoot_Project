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
<<<<<<< HEAD
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri("https://console-nlu.lingtelli.com/"))//http://httpbin.org:80
=======
                .method(HttpMethod.GET)
                .and()
                .path("/SpringService")
                .filters(f -> f.addRequestHeader("H", "Z"))
                .uri("http://localhost:6663"))//https://console-nlu.lingtelli.com
>>>>>>> 082eeeb232e5a32bfd8a5f4a611e3d1399ae6a3a
                .build();
    }

    @GetMapping("/cl/{id}")
    public String GetController(ServletWebRequest servletWebRequest, @PathVariable("id") String id ) {

        return servletWebRequest.getHeader("H")+id;
    }
}
