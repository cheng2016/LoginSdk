package com.example.loginsdk.bean;


/**
 * Created by mitnick.cheng on 2016/9/19.
 */

public class SecurityResponse<T> {
    private int code;
    private String sign;
    private T data;

    public SecurityResponse() {
    }

    public SecurityResponse(int code, String sign, T data) {
        this.code = code;
        this.sign = sign;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
