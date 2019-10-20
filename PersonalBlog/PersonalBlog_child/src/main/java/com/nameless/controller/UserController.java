package com.nameless.controller;



import com.nameless.model.User;

import com.nameless.model.response.Response;
import com.nameless.model.response.ResultCode;
import com.nameless.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;


    //注册接口
    @PostMapping("/register")
    public Response registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    //登录接口
    @PostMapping("/login")
    public Response userLogin(@RequestBody User user, HttpServletResponse httpServletResponse) {
        return userService.login(user,httpServletResponse);
    }


    //根据id查找用户名
    @PostMapping("/getAccount")
    public Response getAccount(@RequestParam("userId") String useId) {
        return userService.getAccountById(useId);
    }






}
