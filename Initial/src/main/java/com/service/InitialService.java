package com.service;

import org.springframework.stereotype.Service;

@Service("InitialService")
public class InitialService {

    public String Service() {
        return "Get Service";
    }
}
