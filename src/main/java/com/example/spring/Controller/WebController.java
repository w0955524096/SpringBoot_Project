package com.example.spring.Controller;


import com.example.spring.Service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    WebService webService;

    @RequestMapping()
    public String TestController() {
        return webService.TestService();
    }
}
