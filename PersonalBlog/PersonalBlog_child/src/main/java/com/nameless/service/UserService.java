package com.nameless.service;

import com.nameless.dao.UserRepository;
import com.nameless.model.User;
import com.nameless.model.response.Response;
import com.nameless.model.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Response register(User user) {
        User oldUser =userRepository.getByAccount(user.getAccount());
        if (oldUser!=null){
            return new Response(ResultCode.USEREXISIT);
        }
        userRepository.save(user);
        Response response = new Response(ResultCode.SUCCESS);
        return response;
    }


    public Response login(User user, HttpServletResponse response) {
        User oldUser =userRepository.getByAccount(user.getAccount());
        if (oldUser==null){
            return new Response(ResultCode.USERNOEXISIT);
        }
        if (!oldUser.getPass().equals( user.getPass()) ){
            return new Response(ResultCode.PASSERRO);
        }
        //将用户的id返回给前端
        Cookie cookie = new Cookie("account",String.valueOf( oldUser.getId() ) );
        cookie.setPath("/");
        response.addCookie(cookie);
        return new Response(ResultCode.SUCCESS);
    }

    public Response getAccountById(String useId) {
        Optional<User> optional = userRepository.findById(useId);
        if (optional.isPresent()){
            User user = optional.get();
            String account = user.getAccount();
            Response response = new Response(ResultCode.SUCCESS);
            response.setMessage(account);
            return response;
        }
        return new Response(ResultCode.FAIL);
    }
}
