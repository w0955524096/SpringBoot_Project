package com.example.spring.Controller;


import com.example.spring.Service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class WebController {

    @Autowired
    WebService webService;

    @RequestMapping("/")
    public String TestController() {
        return webService.TestService()+"2";
    }

    @RequestMapping("/url")
    public String UrlController(@RequestParam("url") String url) {
        return webService.UrlService(url);
    }

    @RequestMapping("/lottery/{numberString}")
    public String LotteryController(@PathVariable("numberString") String numberString) {
        return webService.LotteryService(numberString);
    }

    @RequestMapping("/post/{url}")
    public String PostController(@PathVariable("url") String url) {
        return webService.LotteryService(url);
    }

    @RequestMapping("/scheduled")
    public String ScheduledController() {
        return webService.ScheduledService();
    }

    /**
     * 文件上传类
     * 文件会自动绑定到MultipartFile中
     *
     * @param request     获取请求信息
     * @param description 文件描述
     * @param file        上传的文件
     * @return 上传成功或失败结果
     * @throws IOException
     * @throws IllegalStateException
     */
    @PostMapping("/upload")
    public String upload(HttpServletRequest request,
                         @RequestParam("description") String description,
                         @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {

        return webService.UploadService(request, description, file);
    }
}
