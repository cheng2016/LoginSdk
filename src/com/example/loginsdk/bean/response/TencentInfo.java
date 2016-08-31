package com.example.loginsdk.bean.response;

/**
 * Created by mitnick.cheng on 2016/8/31.
 */

public class TencentInfo {
    private String userId;
    private String userName;

    public TencentInfo(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
