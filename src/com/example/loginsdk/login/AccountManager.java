package com.example.loginsdk.login;

import com.example.loginsdk.bean.response.User;

import android.app.Activity;


/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class AccountManager {
    public final static int PORTRAIT = 0;

    public final static int LANDSCAPE = 1;

    private static Activity mActivity;

    private static LoginCallback mLoginCallback;

    private static User user;

    public static void openYYHLoginActivity(Activity activity) {
        mActivity = activity;
        MGLoginActivity.launch(activity);
    }

    public static Activity getActivity() {
        return mActivity;
    }

    public static void setActivity(Activity mActivity) {
        AccountManager.mActivity = mActivity;
    }

    public static LoginCallback getLoginCallback() {
        return mLoginCallback;
    }

    public static void setLoginCallback(LoginCallback mLoginCallback) {
        AccountManager.mLoginCallback = mLoginCallback;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AccountManager.user = user;
    }
}
