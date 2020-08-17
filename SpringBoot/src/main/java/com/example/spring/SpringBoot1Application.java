package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class SpringBoot1Application {

    public static void main(String[] args) {
        //SpringApplication.run(SpringBoot1Application.class, args);
        ArrayList arrayLists=new ArrayList(Arrays.asList("古娃娃", "處女座", "喜歡喝茶吃魚"));
        System.out.println(arrayLists.toString().replace("[","['").replace("]","']").replace(",","',").replace(" ","'"));

    }

}
