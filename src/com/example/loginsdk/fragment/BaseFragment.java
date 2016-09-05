package com.example.loginsdk.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.example.loginsdk.controller.MGCommitDialog;
import com.example.loginsdk.util.GlobalUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by mitnick.cheng on 2016/8/24.
 */

public class BaseFragment extends Fragment{

    private ProgressDialog mProgressDialog;

    protected MGCommitDialog loginDialog;
    protected MGCommitDialog netWorkDialog;

    protected void showDialog(String var1) {
        if(this.netWorkDialog == null) {
            this.netWorkDialog = new MGCommitDialog(this.getActivity());
        }
        this.netWorkDialog.setMessage(TextUtils.isEmpty(var1)?"loading...":var1);
        this.netWorkDialog.show();
    }

    protected void showLoginDialog(String var1) {
        if(this.loginDialog == null) {
            this.loginDialog = new MGCommitDialog(getActivity());
            Window var2;
            WindowManager.LayoutParams params = (var2 = this.loginDialog.getWindow()).getAttributes();
            var2.setGravity(49);
            params.y = GlobalUtils.a(getActivity(), 80);
            var2.setAttributes(params);
        }
        this.loginDialog.setMessage(TextUtils.isEmpty(var1)?"loading...":var1);
        if(!this.loginDialog.isShowing())
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(Object event){
    };

    public void showProgressDialog(String message) {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(message);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog(){
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        mProgressDialog = null;
    }
}
