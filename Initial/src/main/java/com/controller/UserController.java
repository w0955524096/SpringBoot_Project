package com.controller;

import com.bean.User;
import com.service.UserService;
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
    @PostMapping(value ="/query/byName",headers = "Content-Type=application/json")
    public String testQuery(@RequestBody Map<String,String> map) {
        return userService.selectUserByName(map.get("name")).toString();
    }

    //curl localhost:8333/user/query/all
    @GetMapping(value ="/query/all")
    public String queryAll() {
        return userService.selectAllUser().toString().replace("},","}\n<br>").replace("[","[\n<br>");
    }

    //http://localhost:8333/user/insert?name=Annay&age=18&money=1000
    @RequestMapping("/insert")
    public List<User> testInsert(@RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("money") Double money) {

        userService.insertService(name, age, money);
        return userService.selectAllUser();
    }

    //http://localhost:8333/user/update?id=5&name=Kelly&age=18&money=1000
    @RequestMapping("/update")
    public List<User> testchangemoney(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("money") Double money) {
        userService.changemoney(id, name, age, money);
        return userService.selectAllUser();
    }

    //http://localhost:8333/user/delete?id=6
    @RequestMapping("/delete")
    public List<User> testDelete(@RequestParam("id") int id) {
        userService.deleteService(id);

        return userService.selectAllUser();
    }

}