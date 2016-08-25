package com.example.loginsdk.login;

import com.example.loginsdk.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by mitnick.cheng on 2016/8/16.
 */

public class QuickLoginFragment extends Fragment{
    private OnLoginFragmentListener onLoginFragmentListener;
    public void setOnLoginFragmentListener(OnLoginFragmentListener onLoginFragmentListener) {
        this.onLoginFragmentListener = onLoginFragmentListener;
    }

    private View rootView;
    private TextView tv_header_back;

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
            rootView  = inflater.inflate(R.layout.yyh_fragment_fast_login,container,false);
        }else if(orientation == AccountManager.LANDSCAPE){
            rootView  = inflater.inflate(R.layout.yyh_fragment_fast_login_l,container,false);
        }

        tv_header_back = (TextView) rootView.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginFragmentListener.exit();
            }
        });

        return rootView ;
    }
}
