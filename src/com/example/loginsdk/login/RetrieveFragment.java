package com.example.loginsdk.login;

import com.example.loginsdk.R;
import com.example.loginsdk.activity.BaseFragment;
import com.example.loginsdk.bean.JsonResult;
import com.example.loginsdk.bean.UserRequest;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.LoginApi;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.util.AppUtils;
import com.example.loginsdk.util.MD5Util;
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
        if(orientation == AccountManager.PORTRAIT){
            rootView  = inflater.inflate(R.layout.yyh_fragment_retrieve,container,false);
        }else if(orientation == AccountManager.LANDSCAPE){
            rootView  = inflater.inflate(R.layout.yyh_fragment_retrieve_l,container,false);
        }

        timeCount = new RetrieveFragment.TimeCount(60000, 1000);
        initView();
        initEvent();

        return rootView ;
    }

    public void initView(){
        tv_header_back = (TextView) rootView.findViewById(R.id.tv_header_back);
        btn_code = (Button) rootView.findViewById(R.id.btn_code);
        phone = (EditText) rootView.findViewById(R.id.phone);
        verifyCode = (EditText) rootView.findViewById(R.id.verifyCode);
        password = (EditText) rootView.findViewById(R.id.password);
        password_temp = (EditText) rootView.findViewById(R.id.password_temp);
        phone = (EditText) rootView.findViewById(R.id.phone);
        btn_confirm = (Button) rootView.findViewById(R.id.btn_confirm);
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
                AppUtils.hideKeyWord(getActivity(),v);
                String phoneStr = phone.getText().toString();
                if(TextUtils.isEmpty(phoneStr)){
                    T.showShort(getActivity(),"账户不能为空");
                }else if(!RegularUtils.isMobile(phoneStr)){
                    T.showShort(getActivity(),"手机号格式有误");
                }else {
                    showProgressDialog("获取中...");
                    LoginImpl.getInstance().getCode(phoneStr,LoginApi.RETRIEVE);
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
                    passwordStr = MD5Util.md5(passwordStr);
                    LoginImpl.getInstance().retrieve(token,new UserRequest(phoneStr,passwordStr,verifyCodeStr));
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
}
