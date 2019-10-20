package com.nameless.dao;

import com.nameless.model.Friend;
import com.nameless.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend,String> {




    Friend findByUser1AndUser2(String client, String target);
}
