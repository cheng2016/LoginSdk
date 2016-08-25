package com.example.loginsdk.login;

import com.example.loginsdk.R;
import com.example.loginsdk.activity.BaseFragment;
import com.example.loginsdk.util.AppUtils;
import com.example.loginsdk.util.MResource;
import com.example.loginsdk.util.RegularUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.loginsdk.util.T;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class LoginFragment extends BaseFragment {
	private OnLoginFragmentListener onLoginFragmentListener;

	public void setOnLoginFragmentListener(OnLoginFragmentListener onLoginFragmentListener) {
		this.onLoginFragmentListener = onLoginFragmentListener;
	}

	private View rootView;
	private Button login;
	private ImageView iv_close;

	private TextView quick_login, regist, retrieve_password;

	private EditText userNameEdit, passwordEdit;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (this.onLoginFragmentListener == null)
			throw new RuntimeException("onLoginFragmentListener can not null");
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		Bundle data = getArguments();
		int orientation = data.getInt("orientation", 1);
		
		
		
		if (orientation == AccountManager.PORTRAIT) {
			rootView = inflater.inflate(MResource.getIdByName(getActivity().getApplication(), "layout", "yyh_fragment_login"), container, false);
		} else if (orientation == AccountManager.LANDSCAPE) {
			rootView = inflater.inflate(MResource.getIdByName(getActivity().getApplication(), "layout", "yyh_fragment_login_l") ,container, false);
		}
		login = (Button) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "login"));
		iv_close = (ImageView) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "iv_close"));
		quick_login = (TextView) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "quick_login"));
		regist = (TextView) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "regist"));
		retrieve_password = (TextView) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "retrieve_password"));

		
		
		userNameEdit = (EditText) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "username"));
		passwordEdit = (EditText) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "password"));

		initEvent();
		return rootView;
	}

	private void initEvent() {
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppUtils.hideKeyWord(getActivity(), v);
				String username = userNameEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
					T.showShort(getActivity(), "请输入账号");
				} else if (TextUtils.isEmpty(password)) {
					T.showShort(getActivity(), "请输入密码");
				} else if (!RegularUtils.isMobile(username)) {
					T.showShort(getActivity(), "请输入正确的手机号");
				} else {
					onLoginFragmentListener.login(username, password);
				}
			}
		});
		iv_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLoginFragmentListener.exit();
			}
		});
		quick_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLoginFragmentListener.startFragment(new QuickLoginFragment());
			}
		});
		regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLoginFragmentListener.startFragment(new RegistFragment());
			}
		});
		retrieve_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLoginFragmentListener.startFragment(new RetrieveFragment());
			}
		});
	}
}
