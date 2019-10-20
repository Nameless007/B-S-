package com.nameless.netty;


import com.nameless.model.Record;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Message {
    private Integer type; // 消息类型,用于识别是刚登陆，还是发生消息，还是签收消息
    private Integer type2; // 消息类型，用于识别是系统消息，还是用户消息
    private Record record;    // 聊天记录
    private Object ext;  // 扩展消息字段

}
