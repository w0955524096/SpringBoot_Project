package com.example.demo.Controller;

import com.example.demo.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test1")
    public String TestController() {

        return testService.TestService();
    }
}
