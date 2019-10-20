package com.nameless.model.response;


import lombok.ToString;

@ToString
public enum ResultCode {

    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,11111,"操作失败！"),
    USEREXISIT(false,12000,"用户名已经被注册！"),
    USERNOEXISIT(false,12001,"该用户未注册"),
    PASSERRO(false,12002,"密码错误"),
    ISFRIEND(false,12003,"已经是好友，不用再添加"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    INVALID_PARAM(false,10003,"非法参数！"),
    COURSE_NO_ID(false,10004,"该课程的id不存在！");



    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private ResultCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
        Response response = new Response();
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
