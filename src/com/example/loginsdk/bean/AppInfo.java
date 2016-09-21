package com.example.loginsdk.bean;

/**
 * Created by mitnick.cheng on 2016/9/18.
 */

public class AppInfo {
    private String appId;
    private String appKey;
    private String appSecret;
    private String md5Key;
    private String publicKey;
    private String privateKey;

    public AppInfo() {
    }

    public AppInfo(String appId, String appKey, String appSecret, String md5Key, String publicKey, String privateKey) {
        this.appId = appId;
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.md5Key = md5Key;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
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

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
