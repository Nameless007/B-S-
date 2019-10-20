package com.nameless.controller;


import com.nameless.model.response.Response;
import com.nameless.service.FriendServiece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("api/friend")
public class FriendController {

    @Autowired
    FriendServiece friendServiece;

    //添加好友接口
    @PostMapping("/addFriend")
    public Response add(@RequestParam("client")String client, @RequestParam("target")String target ) {
        HashMap<String, String> map = new HashMap<>();
        map.put("client",client);
        map.put("target",target);
        return friendServiece.add(map);
    }


    //查找该用户的所有好友
    @GetMapping("/findFriend")
    public Response findFriend(@RequestParam("account")String account ) throws Exception{
//         String aaccount = account;
//        account = new String(account.getBytes("utf-8"),"iso8859-1");
        return friendServiece.findFriend(account);
    }




}
