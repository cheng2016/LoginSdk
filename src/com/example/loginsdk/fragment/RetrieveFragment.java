package com.example.loginsdk.fragment;

import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.bean.request.UserRequest;
import com.example.loginsdk.listener.OnLoginFragmentListener;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.LoginApi;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.security.MD5Utils;
import com.example.loginsdk.util.AppUtils;
import com.example.loginsdk.util.MResource;
import com.example.loginsdk.util.RegularUtils;
import com.example.loginsdk.util.ResUtils;
import com.example.loginsdk.util.T;

import android.content.pm.ActivityInfo;
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

public class RetrieveFragment extends BaseFragment {
    private OnLoginFragmentListener onLoginFragmentListener;
    public void setOnLoginFragmentListener(OnLoginFragmentListener onLoginFragmentListener) {
        this.onLoginFragmentListener = onLoginFragmentListener;
    }

    private View rootView;
    private TextView tv_header_back;

    private Button btn_code,btn_confirm;
    private EditText phone,verifyCode,password,password_temp;

    private CountDownTimer timeCount;

    private String token = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.onLoginFragmentListener == null)
            throw new RuntimeException("onLoginFragmentListener can not null");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle data = getArguments();
        int orientation = data.getInt("orientation",1);
        
        if(orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            rootView  = inflater.inflate(MResource.getIdByName(getActivity().getApplication(), "layout", "yyh_fragment_retrieve_l"),container,false);
        }else{
            rootView  = inflater.inflate(MResource.getIdByName(getActivity().getApplication(), "layout", "yyh_fragment_retrieve"),container,false);
        }
        timeCount = new TimeCount(60000, 1000);
        initView();
        initEvent();

        return rootView ;
    }

    public void initView(){
    	tv_header_back = (TextView) rootView.findViewById(ResUtils.getId("tv_header_back"));
        btn_code = (Button) rootView.findViewById(ResUtils.getId("btn_code"));
        phone = (EditText) rootView.findViewById(ResUtils.getId("phone"));
        verifyCode = (EditText) rootView.findViewById(ResUtils.getId("verifyCode"));
        password = (EditText) rootView.findViewById(ResUtils.getId("password"));
        password_temp = (EditText) rootView.findViewById(ResUtils.getId("password_temp"));
        btn_confirm = (Button) rootView.findViewById(ResUtils.getId("btn_confirm"));

    	
//        tv_header_back = (TextView) rootView.findViewById(R.id.tv_header_back);
//        btn_code = (Button) rootView.findViewById(R.id.btn_code);
//        phone = (EditText) rootView.findViewById(R.id.phone);
//        verifyCode = (EditText) rootView.findViewById(R.id.verifyCode);
//        password = (EditText) rootView.findViewById(R.id.password);
//        password_temp = (EditText) rootView.findViewById(R.id.password_temp);
//        btn_confirm = (Button) rootView.findViewById(R.id.btn_confirm);
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
                    showProgressDialog("获取中...");
                    LoginImpl.getInstance(getActivity()).getCode(phoneStr,LoginApi.RETRIEVE);
                }
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener(){
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
                    showProgressDialog("修改中...");
                    passwordStr = MD5Utils.md5(passwordStr);
                    LoginImpl.getInstance(getActivity()).retrieve(token,new UserRequest(phoneStr,passwordStr,verifyCodeStr));
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
            if(message.equals(LoginApi.RETRIEVE)){
                T.showShort(getActivity(),"修改成功");
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

    public static RetrieveFragment newInstance(int var0) {
        RetrieveFragment var1 = new RetrieveFragment();
        Bundle var2;
        (var2 = new Bundle()).putInt("orientation", var0);
        var1.setArguments(var2);
        return var1;
    }
}
