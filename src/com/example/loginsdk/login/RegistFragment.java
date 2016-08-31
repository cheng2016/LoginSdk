package com.example.loginsdk.login;

import com.example.loginsdk.activity.BaseFragment;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.bean.request.UserRequest;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.LoginApi;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.util.AppUtils;
import com.example.loginsdk.util.MD5Util;
import com.example.loginsdk.util.MResource;
import com.example.loginsdk.util.RegularUtils;
import com.example.loginsdk.util.T;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mitnick.cheng on 2016/8/16.
 */

public class RegistFragment extends BaseFragment {
    private OnLoginFragmentListener onLoginFragmentListener;
    public void setOnLoginFragmentListener(OnLoginFragmentListener onLoginFragmentListener) {
        this.onLoginFragmentListener = onLoginFragmentListener;
    }

    private View rootView;
    private TextView tv_header_back;

    private Button btn_code,btn_regist;
    private EditText phone,verifyCode,password,password_temp;

    private CountDownTimer timeCount;

    private String token = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.onLoginFragmentListener == null)
            throw new RuntimeException("onLoginFragmentListener can not null");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        Bundle data = getArguments();
        int orientation = data.getInt("orientation",1);
        if(orientation == AccountManager.PORTRAIT){
            rootView  = inflater.inflate(MResource.getIdByName(getActivity().getApplication(), "layout", "yyh_fragment_regist"),container,false);
        }else if(orientation == AccountManager.LANDSCAPE){
            rootView  = inflater.inflate(MResource.getIdByName(getActivity().getApplication(), "layout", "yyh_fragment_regist_l"),container,false);
        }

        timeCount = new TimeCount(60000, 1000);
        initView();
        initEvent();

        return rootView ;
    }

    public void initView(){
        tv_header_back = (TextView) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "tv_header_back"));
        btn_code = (Button) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "btn_code"));
        phone = (EditText) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "phone"));
        verifyCode = (EditText) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "verifyCode"));
        password = (EditText) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "password"));
        password_temp = (EditText) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "password_temp"));
        btn_regist = (Button) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "btn_regist"));
    }

    public void initEvent(){
        tv_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginFragmentListener.exit();
            }
        });
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneStr = phone.getText().toString();
                if(TextUtils.isEmpty(phoneStr)){
                    T.showShort(getActivity(),"账户不能为空");
                }else if(!RegularUtils.isMobile(phoneStr)){
                    T.showShort(getActivity(),"手机号格式有误");
                }else {
                    showProgressDialog("获取验证码...");
                    LoginImpl.getInstance(getActivity()).getCode(phoneStr, LoginApi.REGIST);
                }
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AppUtils.hideKeyWord(getActivity(),v);

                String phoneStr = phone.getText().toString();
                String verifyCodeStr = verifyCode.getText().toString();
                String passwordStr = password.getText().toString();
                String passwordTempStr = password_temp.getText().toString();

                if(TextUtils.isEmpty(phoneStr)){
                    T.showShort(getActivity(),"请输入账号");
                }else if(TextUtils.isEmpty(verifyCodeStr)){
                    T.showShort(getActivity(),"请输入验证码");
                }else if(TextUtils.isEmpty(passwordStr)){
                    T.showShort(getActivity(),"请输入密码");
                }else if(TextUtils.isEmpty(passwordTempStr)){
                    T.showShort(getActivity(),"请再次输入密码");
                }else if(!passwordStr.equals(passwordTempStr)){
                    T.showShort(getActivity(),"二次密码不一致");
                }else{
                    showProgressDialog("注册中...");
                    passwordStr = MD5Util.md5(passwordStr);
                    LoginImpl.getInstance(getActivity()).regist(token,new UserRequest(phoneStr,passwordStr,verifyCodeStr));
                }
            }
        });
    }

    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        hideProgressDialog();
        if(event instanceof JsonResult){
            JsonResult jsonResult = (JsonResult) event;
            String message = jsonResult.getMessage();
            if(message.equals(LoginApi.REGIST)){
                T.showShort(getActivity(),"注册成功");
                onLoginFragmentListener.exit();
            }else if(message.equals("getCode")){
                timeCount.start();
                token = (String) jsonResult.getData();
                T.showShort(getActivity(),"获取验证码成功");
            }
        }
        if(event instanceof FailedEvent){
            int type = ((FailedEvent) event).getType();
            String message = (String) ((FailedEvent) event).getObject();
            T.showShort(getActivity(),message);
        }
    }

    @Override
    public void onDestroy() {
        super.onStop();
        if (timeCount!=null)
        {
            timeCount.cancel();
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            btn_code.setText("发送激活码");
            btn_code.setEnabled(true);
        }

        public void onTick(long millisUntilFinished) {
            btn_code.setEnabled(false);
            btn_code.setText("在" + millisUntilFinished / 1000 + "秒");
        }
    }
}
