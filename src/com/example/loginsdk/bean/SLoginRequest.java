package com.example.loginsdk.bean;

/**
 * Created by mitnick.cheng on 2016/9/19.
 */

public class SLoginRequest {
    private String appId;
    private String appKey;
    private String phone;
    private String password;

    public SLoginRequest() {
    }

    public SLoginRequest(String appId, String appKey, String phone, String password) {
        this.appId = appId;
        this.appKey = appKey;
        this.phone = phone;
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
