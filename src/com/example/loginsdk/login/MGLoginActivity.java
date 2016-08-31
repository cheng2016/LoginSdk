package com.example.loginsdk.login;

import com.example.loginsdk.activity.BaseActivity;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.bean.request.LoginRequest;
import com.example.loginsdk.bean.response.User;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.net.MessageType;
import com.example.loginsdk.util.LogUtils;
import com.example.loginsdk.util.MD5Util;
import com.example.loginsdk.util.MResource;
import com.example.loginsdk.util.MangoUtils;
import com.example.loginsdk.util.PreferenceConstants;
import com.example.loginsdk.util.PreferenceUtils;
import com.example.loginsdk.util.T;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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

    //第三方qq登录
    public static Tencent mTencent;
    public static String APP_ID = "1105653748";

//    public static String APP_ID = "101252472";

    private BaseUiListener loginListener;

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
    }

    @Override
    public void initData() {
        loginListener = new BaseUiListener(this);
    }

    @Override
    public void initEvent() {
        startLogin();
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
        PreferenceUtils.setPrefString(MGLoginActivity.this, PreferenceConstants.ACCOUNT,account);
        PreferenceUtils.setPrefString(MGLoginActivity.this, PreferenceConstants.PASSWORD,password);
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
            if(jsonResult.getMessage().equals("login")){
                mLoginCallback.onLoginSuccess(MGLoginActivity.this,jsonResult.getData());
                finish();
            }
            if(jsonResult.getMessage().equals("wx_login")){
//              T.showShort(MGLoginActivity.this,(String)jsonResult.getData());
                mLoginCallback.onLoginSuccess(MGLoginActivity.this,null);
                finish();
            }
            if(jsonResult.getMessage().equals("qq_login")){
//                T.showShort(MGLoginActivity.this,(String)jsonResult.getData());
                mLoginCallback.onLoginSuccess(MGLoginActivity.this,null);
                finish();
            }
            if(jsonResult.getMessage().equals("qqLogin")){
//                T.showShort(MGLoginActivity.this,(String)jsonResult.getData());
                mLoginCallback.onLoginSuccess(MGLoginActivity.this,jsonResult.getData());
                finish();
            }
        }
        if(event instanceof FailedEvent){
            int type = ((FailedEvent) event).getType();
            String message = (String) ((FailedEvent) event).getObject();
            if(type == MessageType.LOGIN){
                PreferenceUtils.setPrefString(MGLoginActivity.this, PreferenceConstants.ACCOUNT,"");
                PreferenceUtils.setPrefString(MGLoginActivity.this, PreferenceConstants.PASSWORD,"");
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

    @Override
    public void qqLogin() {
        if(mTencent == null){
            mTencent = Tencent.createInstance(APP_ID,this);
        }
        if (!mTencent.isSessionValid()) {
            mTencent.login(MGLoginActivity.this, "all", loginListener);
        } else {
            mTencent.logout(this);
        }
//        showProgressDialog("login...");
//        LoginImpl.getInstance(MGLoginActivity.this).qqLogin("ftx");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private final class BaseUiListener implements IUiListener
    {
        private Context context;
        //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
        public BaseUiListener (Context context)
        {
            this.context = context;
        }
        @Override
        public void onComplete(Object o)
        {
            JSONObject object = (JSONObject)o;
            doComplete(object);
            Log.i("BaseUiListener","qq登录成功");
        }
        //在这里可以做一些登录成功的处理
        protected void doComplete(JSONObject values)
        {
            initOpenidAndToken(values);
            UserInfo userInfo = new UserInfo(context,mTencent.getQQToken());
            userInfo.getUserInfo(new UserInfoUiLIstener());
        }
        //在这里可以做登录失败的处理
        @Override
        public void onError(UiError e)
        {
            Log.i("BaseUiListener","onError");
        }
        //在这里可以做登录被取消的处理
        @Override
        public void onCancel()
        {
            Log.i("BaseUiListener","onCancel");
        }
    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }

    private final class UserInfoUiLIstener implements IUiListener
    {
        @Override
        public void onComplete(Object o)
        {
            doComplete((JSONObject) o);
        }
        protected void doComplete(JSONObject response)
        {
            try
            {
                if (response.has("nickname")) {
                    try {
                        Log.i("UserInfoUiLIstener",response.getString("nickname"));
                        Log.i("UserInfoUiLIstener",response.getString("figureurl"));
                        Log.i("UserInfoUiLIstener",response.getString("nickname"));
//                        EventBus.getDefault().post(new JsonResult(200,"qq_login",response.getString("nickname")));
                        showProgressDialog("login...");
                        LoginImpl.getInstance(MGLoginActivity.this).qqLogin(response.getString("nickname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("UserInfoUiLIstener",response.toString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(UiError uiError)
        {
            Log.i("UserInfoUiLIstener","onError");
        }

        @Override
        public void onCancel()
        {
            Log.i("UserInfoUiLIstener","onCancel");
        }
    }
}