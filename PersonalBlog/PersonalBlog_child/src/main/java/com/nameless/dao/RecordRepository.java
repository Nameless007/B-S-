package com.nameless.dao;

import com.nameless.model.Friend;
import com.nameless.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,String> {

    List<Record> findByUserid(String userid);


    List<Record> findByUseridAndFriendidAndHaveread(String friend, String user, int i);

    List<Record> findByUseridAndHaveread(String account, int i);

    List<Record> findByFriendidAndHaveread(String account, int i);
}
