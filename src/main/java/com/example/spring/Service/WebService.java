package com.example.spring.Service;

import org.springframework.stereotype.Service;

@Service("WebService")
public class WebService {

    public String TestService() {

        return "test";
    }
}
