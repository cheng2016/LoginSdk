package com.example.loginsdk.listener;

import android.app.Activity;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public interface LoginCallback<T> {
    void onLoginSuccess(Activity var1, T var2);

    void onLoginCancel();

    void onLoginError(Activity var1, String message);
}
