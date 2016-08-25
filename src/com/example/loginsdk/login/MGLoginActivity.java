package com.example.loginsdk.login;

import com.example.loginsdk.R;
import com.example.loginsdk.activity.BaseActivity;
import com.example.loginsdk.bean.JsonResult;
import com.example.loginsdk.bean.LoginRequest;
import com.example.loginsdk.bean.User;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.net.MessageType;
import com.example.loginsdk.util.LogUtils;
import com.example.loginsdk.util.MD5Util;
import com.example.loginsdk.util.MResource;
import com.example.loginsdk.util.MangoUtils;
import com.example.loginsdk.util.T;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class MGLoginActivity extends BaseActivity implements OnLoginFragmentListener {
    private final static String TAG = "MGLoginActivity";

    private LoginCallback mLoginCallback;

    private LoginFragment mLoginFragment;

    private RegistFragment mRegistFragment;

    private QuickLoginFragment mQuickLoginFragment;

    private RetrieveFragment mRetrieveFragment;

    private int orientation;

    private Fragment currentFragment;

    @Override
    public void initView() {
        //         设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        orientation = MangoUtils.getIntMetaData(this,"landscape");
        if(orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        
        setContentView(MResource.getIdByName(getApplication(), "layout", "yyh_activity_login"));
//        getSupportActionBar().hide();

        this.mLoginCallback = AccountManager.getLoginCallback();
        this.getWindow().setFlags(1024, 1024);


        if(orientation == AccountManager.PORTRAIT){
            Display display = getWindowManager().getDefaultDisplay();
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.width = (int) (display.getWidth() * 0.9);
            layoutParams.height = (int)((double)display.getHeight() * 0.7);
            getWindow().setAttributes(layoutParams);
            getWindow().setGravity(16);
        }else if(orientation == AccountManager.LANDSCAPE){
            Display display = getWindowManager().getDefaultDisplay();
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.width = (int) (display.getWidth() * 0.7);
            layoutParams.height = (int)((double)display.getHeight() * 0.9);
            getWindow().setAttributes(layoutParams);
            getWindow().setGravity(16);
        }
        startLogin();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    public void startLogin() {
        LogUtils.i(TAG,"startLogin");
        mLoginFragment = new LoginFragment();
        startFragment(mLoginFragment);
    }

    public static void launch(Activity activity) {
        Intent intent= new Intent(activity, MGLoginActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void login(String account,String password) {
        showProgressDialog("loading...");
        password = MD5Util.md5(password);
        LoginImpl.getInstance(this).login(new LoginRequest(account,password));
    }

    @Override
    public void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        Bundle bundle  = new Bundle();
        bundle.putInt("orientation",orientation);
        if(fragment instanceof LoginFragment){
            if(mLoginFragment == null){
                mLoginFragment = new LoginFragment();
            }
            mLoginFragment.setArguments(bundle);
            mLoginFragment.setOnLoginFragmentListener(this);
            fragment = mLoginFragment;
        }else if(fragment instanceof RegistFragment){
            if(mRegistFragment == null){
                mRegistFragment = new RegistFragment();
            }
            mRegistFragment.setArguments(bundle);
            mRegistFragment.setOnLoginFragmentListener(this);
            fragment = mRegistFragment;
        }else if(fragment instanceof QuickLoginFragment){
            if(mQuickLoginFragment == null){
                mQuickLoginFragment = new QuickLoginFragment();
            }
            mQuickLoginFragment.setArguments(bundle);
            mQuickLoginFragment.setOnLoginFragmentListener(this);
            fragment = mQuickLoginFragment;
        }else if(fragment instanceof RetrieveFragment){
            if(mRetrieveFragment == null){
                mRetrieveFragment = new RetrieveFragment();
            }
            mRetrieveFragment.setArguments(bundle);
            mRetrieveFragment.setOnLoginFragmentListener(this);
            fragment = mRetrieveFragment;
        }
        this.currentFragment = fragment;

        fragmentTransaction.replace(MResource.getIdByName(getApplication(), "id", "content"),fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if(currentFragment instanceof LoginFragment){
            return;
        }else{
            startFragment(mLoginFragment);
        }
    }

    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        hideProgressDialog();
        if(event instanceof JsonResult){
            JsonResult jsonResult = (JsonResult) event;
            if(jsonResult.getData() instanceof User){
                mLoginCallback.onLoginSuccess(MGLoginActivity.this,jsonResult.getData());
                finish();
            }
        }
        if(event instanceof FailedEvent){
            int type = ((FailedEvent) event).getType();
            String message = (String) ((FailedEvent) event).getObject();
            if(type == MessageType.LOGIN){
                mLoginCallback.onLoginError(MGLoginActivity.this,message);
            }else{
                T.showShort(MGLoginActivity.this,message);
            }
        }

    }

    @Override
    public void exit() {
        if(currentFragment instanceof LoginFragment){
            finish();
        }else{
            startFragment(mLoginFragment);
        }
    }
}
