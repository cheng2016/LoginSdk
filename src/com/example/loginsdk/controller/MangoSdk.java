package com.example.loginsdk.controller;

import android.app.Activity;

import com.example.loginsdk.listener.LoginCallback;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class MangoSdk {
    public static void login(Activity activity, LoginCallback loginCallback) {
        AccountManager.setLoginCallback(loginCallback);
        AccountManager.openYYHLoginActivity(activity);
    }
}
