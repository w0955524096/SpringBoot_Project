package com.controller;

import com.service.InitialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitialController {

    @Autowired
    private InitialService InitialService;

    @GetMapping("/test/{id}")
    public String controller(@PathVariable("id") String id) {

        return "this is home:<BR>"+id+"<BR>"+InitialService.Service();
    }

    @GetMapping("/")
    public String urlController(@RequestParam("url") String url) {
        if (url.contains(".jpg")||url.contains(".png"))
            return "<textarea  cols=200><img src=\">"+url+"\"></textarea>";

        return "<textarea  cols=200><a href=\""+url+"\" target=\"_blank\">"+url+"</a></textarea>";
    }
}
