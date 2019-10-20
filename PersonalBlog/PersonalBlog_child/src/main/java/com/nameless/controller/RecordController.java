package com.nameless.controller;


import com.nameless.model.User;
import com.nameless.model.response.Response;
import com.nameless.model.response.ResponseHasInitMsg;
import com.nameless.model.response.ResponseHasRecords;
import com.nameless.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/message")
public class RecordController {



    @Autowired
    private RecordService recordService;

    //
    @GetMapping("/read/{account}")
    public ResponseHasRecords getRecord(@PathVariable("account")String account ) {

        return recordService.getRecord(account);

    }



    //  user查找 friend 的留言
    @PostMapping("/read")
    public ResponseHasRecords getRecord(@RequestParam("user") String user,@RequestParam("friend")String friend) {

        return recordService.getRecordByUserAndFriend(user,friend);

    }

    //用户上线时调用的接口，查找未读消息
    @GetMapping("/init")
    public ResponseHasInitMsg init(@RequestParam("account")String account ){
        return recordService.init(account);
    }


}
