package com.example.loginsdk.bean;

/**
 * Created by mitnick.cheng on 2016/9/19.
 */

public class SecurityRequest<T> {
    private String appId;
    private String sign;
    private T data;

    public SecurityRequest() {
    }

    public SecurityRequest(String appId, String sign, T data) {
        this.appId = appId;
        this.sign = sign;
        this.data = data;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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
