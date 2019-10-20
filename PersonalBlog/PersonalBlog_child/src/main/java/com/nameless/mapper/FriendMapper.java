package com.nameless.mapper;


import com.nameless.model.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "FriendMapper")
public interface FriendMapper {

    List<String> findFriendsByAccount(String account);

    List<Friend> findAll();

}
