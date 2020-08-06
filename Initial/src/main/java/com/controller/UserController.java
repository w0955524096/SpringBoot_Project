package com.controller;

import com.bean.User;
import com.service.UserService;
import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //(雙引號注意)
    //curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"loli\"}" localhost:8333/user/query/byName
    @RequestMapping(value ="/query/byName",method = RequestMethod.POST,headers = "Content-Type=application/json")
    public String testQuery(@RequestBody Map<String,String> map) {
        return userService.selectUserByName(map.get("name")).toString();
    }

    //curl -X POST -H "Content-Type: application/json" localhost:8333/user/query/all
    @RequestMapping(value ="/query/all",method = RequestMethod.POST,headers = "Content-Type=application/json")
    public String queryAll() {
        return userService.selectAllUser().toString().replace("}","}\n");
    }

    @RequestMapping("/insert")
    public List<User> testInsert(@RequestParam("name") String id, @RequestParam("age") Integer age, @RequestParam("money") Double money) {

        userService.insertService(id, age, money);
        return userService.selectAllUser();
    }

    @RequestMapping("/changemoney")
    public List<User> testchangemoney() {
        userService.changemoney();
        return userService.selectAllUser();
    }

    @RequestMapping("/delete")
    public String testDelete() {
        userService.deleteService(3);
        return "OK";
    }

}