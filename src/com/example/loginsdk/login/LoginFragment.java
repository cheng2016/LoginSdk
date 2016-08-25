package com.example.loginsdk.login;

import com.example.loginsdk.R;
import com.example.loginsdk.activity.BaseFragment;
import com.example.loginsdk.util.RegularUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.loginsdk.bean.JsonResult;
import com.example.loginsdk.bean.UserRequest;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.LoginApi;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.util.AppUtils;
import com.example.loginsdk.util.MD5Util;
import com.example.loginsdk.util.RegularUtils;
import com.example.loginsdk.util.T;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener{
    private OnLoginFragmentListener onLoginFragmentListener;
    public void setOnLoginFragmentListener(OnLoginFragmentListener onLoginFragmentListener) {
        this.onLoginFragmentListener = onLoginFragmentListener;
    }

    private View rootView;
    private Button login;
    private ImageView iv_close;

    private TextView quick_login,regist,retrieve_password;

    private EditText userNameEdit,passwordEdit;

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
            rootView  = inflater.inflate(R.layout.yyh_fragment_login,container,false);
        }else if(orientation == AccountManager.LANDSCAPE){
            rootView  = inflater.inflate(R.layout.yyh_fragment_login_l,container,false);
        }
        login = (Button) rootView.findViewById(R.id.login);
        iv_close= (ImageView) rootView.findViewById(R.id.iv_close);
        quick_login = (TextView) rootView.findViewById(R.id.quick_login);
        regist = (TextView) rootView.findViewById(R.id.regist);
        retrieve_password = (TextView) rootView.findViewById(R.id.retrieve_password);

        userNameEdit = (EditText) rootView.findViewById(R.id.username);
        passwordEdit = (EditText) rootView.findViewById(R.id.password);

        login.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        quick_login.setOnClickListener(this);
        regist.setOnClickListener(this);
        retrieve_password.setOnClickListener(this);

        return rootView ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                AppUtils.hideKeyWord(getActivity(),v);
                String username = userNameEdit.getText().toString();
                String password =  passwordEdit.getText().toString();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                        T.showShort(getActivity(),"请输入账号");
                }else if(TextUtils.isEmpty(password)){
                    T.showShort(getActivity(),"请输入密码");
                }else if(!RegularUtils.isMobile(username)) {
                    T.showShort(getActivity(),"请输入正确的手机号");
                }else{
                    onLoginFragmentListener.login(username,password);
                }
                break;
            case R.id.iv_close:
                onLoginFragmentListener.exit();
                break;
            case R.id.quick_login:
                onLoginFragmentListener.startFragment(new QuickLoginFragment());
                break;
            case R.id.regist:
                onLoginFragmentListener.startFragment(new RegistFragment());
                break;
            case R.id.retrieve_password:
                onLoginFragmentListener.startFragment(new RetrieveFragment());
                break;
        }
    }
    
    @Override
    public void onEventMainThread(Object event) {
    	// TODO Auto-generated method stub
    	super.onEventMainThread(event);
    }
}
