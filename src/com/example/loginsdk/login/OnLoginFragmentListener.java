package com.example.loginsdk.login;

import android.support.v4.app.Fragment;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public interface OnLoginFragmentListener {
    public void login(String account,String password);

    public void exit();

    public void startFragment(Fragment fragment);
}
