package com.example.loginsdk.bean;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class Account {
    private long id;
    private String phone;
    private String userName;
    private String accessToken;

    public Account() {
    }

    public Account(long id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }

    public Account(long id, String userName, String accessToken) {
        this.id = id;
        this.userName = userName;
        this.accessToken = accessToken;
    }

    public Account(long id, String phone, String userName, String accessToken) {
        this.id = id;
        this.phone = phone;
        this.userName = userName;
        this.accessToken = accessToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
