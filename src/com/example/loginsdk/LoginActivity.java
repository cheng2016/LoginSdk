package com.example.loginsdk;

import com.example.loginsdk.activity.BaseActivity;
import com.example.loginsdk.bean.Account;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.listener.LoginCallback;
import com.example.loginsdk.controller.MangoSdk;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.util.ResUtils;
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
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置全屏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ResUtils.setPkgName(this.getPackageName());
        setContentView(ResUtils.getLayout("activity_login"));
        mTextView = (TextView) findViewById(ResUtils.getId("textView"));
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog("thirdLogin............");
                LoginImpl.getInstance(LoginActivity.this).thirdLogin("my_ftx","1313123123");
            }
        });
    }

    public void showDialog(View view){
        MangoSdk.login(this, new LoginCallback<Account>() {
            @Override
            public void onLoginSuccess(Activity activity, Account user) {
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
        dismissDialog();
        if(event instanceof JsonResult){
            JsonResult jsonResult = (JsonResult) event;
           if(jsonResult.getMessage().equals("checkLogin")) {
                T.showShort(LoginActivity.this,"checkLogin is success");
            }
            if(jsonResult.getMessage().equals("thirdLogin")) {
                T.showShort(LoginActivity.this,"thirdLogin is success");
            }
        }
    }
}
