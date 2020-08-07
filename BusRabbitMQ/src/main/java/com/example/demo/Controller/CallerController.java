package com.example.demo.Controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

public class CallerController {

    //Spring-cloud-bus
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(){
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }
}
