package com.example.loginsdk.bean;

/**
 * Created by mitnick.cheng on 2016/8/24.
 */
public class UserRequest {
    private String phone;
    private String password;
    private String code;

    public UserRequest() {
    }

    public UserRequest(String phone, String password, String code) {
        this.phone = phone;
        this.password = password;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
