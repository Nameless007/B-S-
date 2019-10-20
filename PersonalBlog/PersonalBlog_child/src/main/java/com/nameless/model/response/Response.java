package com.nameless.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Response {


    //操作是否成功
    boolean success = true;

    //操作代码
    int code = 10000;

    //提示信息
    String message;

    public Response(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static Response SUCCESS(){
        return new Response(ResultCode.SUCCESS);
    }
    public static Response FAIL(){
        return new Response(ResultCode.FAIL);
    }
}
