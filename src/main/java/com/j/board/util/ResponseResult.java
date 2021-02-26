package com.j.board.util;

public class ResponseResult {
    String msg;
    Object body;

    public ResponseResult(String msg) {
        this.msg = msg;
    }

    public ResponseResult(Object body) {
        this.body = body;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return this.body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

}