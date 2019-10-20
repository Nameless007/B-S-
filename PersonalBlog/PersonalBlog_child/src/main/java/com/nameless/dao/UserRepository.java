package com.nameless.dao;

import com.nameless.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    User getByAccount(String account);
}
