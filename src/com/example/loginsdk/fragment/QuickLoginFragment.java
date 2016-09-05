package com.example.loginsdk.fragment;

import com.example.loginsdk.listener.OnLoginFragmentListener;
import com.example.loginsdk.util.ResUtils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by mitnick.cheng on 2016/8/16.
 */

public class QuickLoginFragment extends BaseFragment {
    private OnLoginFragmentListener onLoginFragmentListener;
    public void setOnLoginFragmentListener(OnLoginFragmentListener onLoginFragmentListener) {
        this.onLoginFragmentListener = onLoginFragmentListener;
    }

    private View rootView;
    private TextView tv_header_back;

    private int orientation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.onLoginFragmentListener == null)
            throw new RuntimeException("onLoginFragmentListener can not null");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){
            Bundle data = getArguments();
            orientation = data.getInt("orientation");
        }
        if(orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){

            rootView  = inflater.inflate(ResUtils.getLayout("yyh_fragment_fast_login_l"),container,false);
        }else{
            rootView  = inflater.inflate(ResUtils.getLayout("yyh_fragment_fast_login"),container,false);
        }

        tv_header_back = (TextView) rootView.findViewById(ResUtils.getId("tv_header_back"));
        tv_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginFragmentListener.exit();
            }
        });

        return rootView ;
    }

    public static QuickLoginFragment newInstance(int var0) {
        QuickLoginFragment var1 = new QuickLoginFragment();
        Bundle var2;
        (var2 = new Bundle()).putInt("orientation", var0);
        var1.setArguments(var2);
        return var1;
    }
}
