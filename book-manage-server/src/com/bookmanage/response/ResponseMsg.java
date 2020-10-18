package com.bookmanage.response;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @Author YongQiang
 * @Date 2020/10/14 13:22
 * @Version 1.0
 */
public class ResponseMsg {

    private Integer code;

    private HashMap<String, Object> data;

    private String msg;

    public ResponseMsg() {
        this.code = 200;
    }

    public ResponseMsg(HashMap<String, Object> data) {
        this.code = 200;
        this.data = data;
    }

    public ResponseMsg(Integer code, HashMap<String, Object> data) {
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        String str=JSONObject.toJSONString(this);
        return str;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
