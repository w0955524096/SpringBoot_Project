package com.example.gateway;

import com.example.gateway.Resolver.GatewayResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	@Bean
	public GatewayResolver gatewayResolver() {
		return new GatewayResolver();
	}

}
