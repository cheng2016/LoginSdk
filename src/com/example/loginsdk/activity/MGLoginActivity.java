package com.example.loginsdk.activity;

import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.listener.LoginCallback;
import com.example.loginsdk.listener.OnLoginFragmentListener;
import com.example.loginsdk.controller.AccountManager;
import com.example.loginsdk.fragment.LoginFragment;
import com.example.loginsdk.fragment.QuickLoginFragment;
import com.example.loginsdk.fragment.RegistFragment;
import com.example.loginsdk.fragment.RetrieveFragment;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.MessageType;
import com.example.loginsdk.util.GlobalUtils;
import com.example.loginsdk.util.L;
import com.example.loginsdk.util.MangoUtils;
import com.example.loginsdk.util.ResUtils;
import com.example.loginsdk.util.T;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class MGLoginActivity extends BaseActivity implements OnLoginFragmentListener {
    protected final static String TAG = "MGLoginActivity";

    private LoginCallback mLoginCallback;

    private LoginFragment mLoginFragment;

    private RegistFragment mRegistFragment;

    private QuickLoginFragment mQuickLoginFragment;

    private RetrieveFragment mRetrieveFragment;

    private int orientation;

    private Fragment currentFragment;

    @Override
    public void initView() {
        this.mLoginCallback = AccountManager.getLoginCallback();
        //         设置全屏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        orientation = MangoUtils.getIntMetaData(this, "orientation");
        this.getWindow().setFlags(1024, 1024);
        GlobalUtils.setScreenOrientation(this, orientation);
        setContentView(ResUtils.getLayout("yyh_activity_login"));
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            GlobalUtils.setDisplayPartams(MGLoginActivity.this, 0.95D, 0.7D);
        } else {
            GlobalUtils.setDisplayPartams(MGLoginActivity.this);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        startLogin();
    }

    public void startLogin() {
        L.i(TAG, "startLogin");
        startFragment(LoginFragment.newInstance(orientation));
    }

    public static void launch(Activity activity) {
        L.i(TAG,activity.getPackageName());
        ResUtils.setPkgName(activity.getPackageName());
        Intent intent = new Intent(activity, MGLoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof LoginFragment) {
            return;
        } else {
            startFragment(mLoginFragment);
        }
    }

    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        dismissDialog();
        if (event instanceof JsonResult) {
            JsonResult jsonResult = (JsonResult) event;
            if (jsonResult.getMessage().equals("thirdLogin")) {
                mLoginCallback.onLoginSuccess(MGLoginActivity.this, jsonResult.getData());
                finish();
            }
        }
        if (event instanceof FailedEvent) {
            int type = ((FailedEvent) event).getType();
            String message = (String) ((FailedEvent) event).getObject();
            if (type == MessageType.LOGIN) {

            } else {
                T.showShort(MGLoginActivity.this, message);
            }
        }
    }

    @Override
    public void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        if (fragment instanceof LoginFragment) {
            if (mLoginFragment == null) {
                mLoginFragment = LoginFragment.newInstance(orientation);
                mLoginFragment.setOnLoginFragmentListener(this);
            }
            fragment = mLoginFragment;
        } else if (fragment instanceof RegistFragment) {
            if (mRegistFragment == null) {
                mRegistFragment = RegistFragment.newInstance(orientation);
                mRegistFragment.setOnLoginFragmentListener(this);
            }
            fragment = mRegistFragment;
        } else if (fragment instanceof QuickLoginFragment) {
            if (mQuickLoginFragment == null) {
                mQuickLoginFragment = QuickLoginFragment.newInstance(orientation);
                mQuickLoginFragment.setOnLoginFragmentListener(this);
            }
            fragment = mQuickLoginFragment;
        } else if (fragment instanceof RetrieveFragment) {
            if (mRetrieveFragment == null) {
                mRetrieveFragment = RetrieveFragment.newInstance(orientation);
                mRetrieveFragment.setOnLoginFragmentListener(this);
            }
            fragment = mRetrieveFragment;
        }
        this.currentFragment = fragment;
        fragmentTransaction.replace(ResUtils.getId("content"), fragment).commit();
    }

    @Override
    public void exit() {
        if (currentFragment instanceof LoginFragment) {
            finish();
        } else {
            startFragment(mLoginFragment);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mLoginFragment == null){
            mLoginFragment = LoginFragment.newInstance(orientation);
        }
        mLoginFragment.onActivityResult(requestCode,resultCode,data);
    }
}