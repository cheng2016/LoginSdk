package com.example.loginsdk.activity;

import com.example.loginsdk.R;
import com.example.loginsdk.bean.Account;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.login.LoginCallback;
import com.example.loginsdk.login.MangoSdk;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.util.T;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mitnick.cheng on 2016/7/28.
 */

public class LoginActivity extends BaseActivity {
    private String mAccessToken = "";

    private TextView mTextView;

    @Override
    public void initView() {
        // 设置全屏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    public void showDialog(View view){
        MangoSdk.login(this, new LoginCallback<Account>() {
            @Override
            public void onLoginSuccess(Activity activity, Account user) {
                T.showShort(LoginActivity.this,"登录成功！");
                LoginImpl.getInstance(activity).checkLogin(user);
            }

            @Override
            public void onLoginCancel() {
                T.showShort(LoginActivity.this,"onLoginCancel");
            }

            @Override
            public void onLoginError(Activity activity, String message) {
                T.showShort(LoginActivity.this,message);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        if(event instanceof JsonResult){
            JsonResult jsonResult = (JsonResult) event;
           if(jsonResult.getMessage().equals("checkLogin")) {
                T.showShort(LoginActivity.this,"checkLogin is success");
            }
        }
    }
}
