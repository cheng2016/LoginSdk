package com.example.loginsdk;

import com.example.loginsdk.activity.BaseActivity;
import com.example.loginsdk.bean.Account;
import com.example.loginsdk.bean.AppInfo;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.listener.InitCallback;
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
        AppInfo appInfo = new AppInfo();
        appInfo.setAppId("101");
        appInfo.setAppKey("cszb");
        appInfo.setAppSecret("AiR1JAHTWGf+5Y0DfWCKGg==");
        appInfo.setMd5Key("9t70pMVJgCa6CllrA8Dhbg==");
        appInfo.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxcGL75WekoM0R/9PmO5kk4PgT+n2Q1NyRviKNnOwLjcBTsYMacFPWW7xrCEB6booLoho83V6UKDIFBfgD4+mmSKS89uPCBRQOCUtNZHRRLaPqMSUG/VLXdlalmWcLC2u5mOvKK57Vfs9q75La0K/NMSoKkrsgqOMAJMSiFhGxkwIDAQAB");
        appInfo.setPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALFwYvvlZ6SgzRH/0+Y7mSTg+BP6fZDU3JG+Io2c7AuNwFOxgxpwU9ZbvGsIQHpuiguiGjzdXpQoMgUF+APj6aZIpLz248IFFA4JS01kdFEto+oxJQb9Utd2VqWZZwsLa7mY68orntV+z2rvktrQr80xKgqSuyCo4wAkxKIWEbGTAgMBAAECgYEAlE0hffVrnY4EN9IBUgFn6wnYbiJDBmTSgUFP8dpA8xsjrw/DSrqtZ5C+txprgaiX2kc+9LX0kZHS1J37f4ziv4IqEl/j2MdCWwwNgRP6js86DdzsZSJeps0MYHImGeFzbP9ow/LGqReKISh8m/UZ+ED08Sfs/FMUeMOMREehmqkCQQDi57Ues6as8OKnuLIQIkQDsodbMydAbPfYvs2xjY4UtlokCpUBEIDlNin4C1TFd+AqDH1+bGMJZHIGDpG555NlAkEAyDDs+JlHpAqv92hl9WLUs8njdbLmly/ogZ2hueB4b+Bh7M6cB/Q4vnfZC4sJP7ydP6lD3fKQxjPdtSzNyL0tlwJAe7EwLPJmM/SEukWW2/Cx/wn9e5vhE9/TJFavLtEp2OSnMuqJ27Cxc0IZktwqdBMrIgD5EEfpnR8igWbDzgINeQJBALP/ObK0rzOR4HIFzlb6i+Ezz27OcoLRsq6IOxfT+rYs/B9eDfEg57xqKh803hYO0xygZsMzDHUJwXjbVhP99PUCQDxEMfqozrHHQq/P1NEYpEOd4x1wHd6i4MnuqXv3Q9I0MA+UvZGBN/+7SsgwXmR2LIwGduoqslqobqiTi8js41c=");
        MangoSdk.init(this, appInfo, new InitCallback() {
            @Override
            public void onInitSuccess() {
                T.showShort(LoginActivity.this,"初始化成功！");
            }

            @Override
            public void onInitFail(String message) {
                T.showShort(LoginActivity.this,"初始化失败！"+message);
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
