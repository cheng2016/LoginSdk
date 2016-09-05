package com.example.loginsdk.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.example.loginsdk.controller.MGCommitDialog;
import com.example.loginsdk.util.GlobalUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Michael Smith on 2016/7/24.
 */

public abstract class BaseActivity extends FragmentActivity{
    protected final static String TAG = "BaseActivity";
    private ProgressDialog mProgressDialog;

    protected MGCommitDialog loginDialog;
    protected MGCommitDialog netWorkDialog;

    protected BaseActivity mActivity;

    protected void showDialog(String var1) {
        if(this.netWorkDialog == null) {
            this.netWorkDialog = new MGCommitDialog(mActivity);
            Window var2;
            WindowManager.LayoutParams var3 = (var2 = mActivity.netWorkDialog.getWindow()).getAttributes();
            var2.setGravity(17);
            var3.y = GlobalUtils.a(this, -20);
            var2.setAttributes(var3);
        }

        this.netWorkDialog.setMessage(TextUtils.isEmpty(var1)?"loading...":var1);
        this.netWorkDialog.show();
    }

    protected void showLoginDialog(String var1) {
        if(this.loginDialog == null) {
            this.loginDialog = new MGCommitDialog(mActivity);
            Window window = this.loginDialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params = window.getAttributes();
            window.clearFlags(2);
            window.setGravity(49);
            params.y = GlobalUtils.a(mActivity, 80);
            window.setAttributes(params);
        }

        this.loginDialog.setMessage(TextUtils.isEmpty(var1)?"loading...":var1);
        this.loginDialog.show();
    }

    protected void dismissDialog() {
        if(this.netWorkDialog != null && this.netWorkDialog.isShowing()) {
            this.netWorkDialog.dismiss();
        }

        if(this.loginDialog != null && this.loginDialog.isShowing()) {
            this.loginDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
        this.mActivity = this;
    }

    public abstract void initView();

    public abstract void initData();

    public abstract void initEvent();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe
    public  void onEventMainThread(Object event){
    };

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void showProgressDialog(String message) {
        if(mProgressDialog == null){
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(message);
                mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog(){
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
