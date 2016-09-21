package com.example.loginsdk.controller;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.example.loginsdk.bean.AppInfo;
import com.example.loginsdk.bean.SLoginRequest;
import com.example.loginsdk.bean.SecurityRequest;
import com.example.loginsdk.listener.InitCallback;
import com.example.loginsdk.listener.LoginCallback;
import com.example.loginsdk.security.AESUtils;
import com.example.loginsdk.security.RSAUtils;
import com.example.loginsdk.util.ResUtils;
import com.google.gson.Gson;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class MangoSdk {
    public static void login(Activity activity, LoginCallback loginCallback) {
        AccountManager.setLoginCallback(loginCallback);
        AccountManager.openYYHLoginActivity(activity);
    }

    public static boolean init(Activity activity, AppInfo appInfo, InitCallback callback){
        ResUtils.setPkgName(activity.getPackageName());
        if(appInfo == null){
            Log.e("YYHSDKAPI", "用户参数CPInfo不正确");
            callback.onInitFail("用户参数CPInfo不能为空");
            return false;
        }else{
            if(TextUtils.isEmpty(appInfo.getAppId())){
                callback.onInitFail("AppId不能为空");
                return false;
            }
            if(TextUtils.isEmpty(appInfo.getAppKey())){
                callback.onInitFail("AppKey 不能为空");
                return false;
            }
            if(TextUtils.isEmpty(appInfo.getAppSecret())){
                callback.onInitFail("appSecret 不能为空");
                return false;
            }
            if(TextUtils.isEmpty(appInfo.getMd5Key())){
                callback.onInitFail("md5Key 不能为空");
                return false;
            }
            if(TextUtils.isEmpty(appInfo.getPublicKey())){
                callback.onInitFail("publicKey 不能为空");
                return false;
            }
            if(TextUtils.isEmpty(appInfo.getPrivateKey())){
                callback.onInitFail("privateKey 不能为空");
                return false;
            }
            callback.onInitSuccess();
            AccountManager.setAppInfo(appInfo);
            return true;
        }
    }

    public static void main(String[] args) throws Exception {

    }
}
