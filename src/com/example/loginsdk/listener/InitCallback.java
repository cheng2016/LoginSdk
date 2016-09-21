package com.example.loginsdk.listener;

import com.example.loginsdk.bean.Account;

/**
 * Created by mitnick.cheng on 2016/9/18.
 */

public interface InitCallback {
    void onInitSuccess();

    void onInitFail(String message);
}
