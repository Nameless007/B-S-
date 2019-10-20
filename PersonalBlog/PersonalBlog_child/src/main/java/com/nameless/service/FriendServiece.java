package com.nameless.service;

import com.alibaba.fastjson.JSON;
import com.nameless.dao.FriendRepository;
import com.nameless.mapper.FriendMapper;
import com.nameless.model.Friend;
import com.nameless.model.response.Response;
import com.nameless.model.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class FriendServiece {

    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendMapper friendMapper;

    //client发起好友申请的一方
    @Transactional
    public Response add(Map<String, String> map) {
        String client = map.get("client");
        String target = map.get("target");
        Friend friend;
        friend = friendRepository.findByUser1AndUser2(client,target);
        if (friend!=null)return new Response(ResultCode.ISFRIEND);
        friend = friendRepository.findByUser1AndUser2(target,client);
        if (friend!=null)return new Response(ResultCode.ISFRIEND);
        friend = new Friend();
        friend.setUser1(client);
        friend.setUser2(target);

        friendRepository.save(friend);
        return new Response(ResultCode.SUCCESS);
    }


    public Response findFriend(String account) {
        List<String> friends = friendMapper.findFriendsByAccount(account);
        Response response = new Response(ResultCode.SUCCESS);
        String jsonString = JSON.toJSONString(friends);
        response.setMessage(jsonString);
        return response;
    }
}
