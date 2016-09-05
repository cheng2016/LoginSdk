package com.example.loginsdk.fragment;

import com.example.loginsdk.controller.AccountManager;
import com.example.loginsdk.bean.request.LoginRequest;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.bean.response.WXUserInfo;
import com.example.loginsdk.listener.BaseUiListener;
import com.example.loginsdk.listener.LoginCallback;
import com.example.loginsdk.listener.OnLoginFragmentListener;
import com.example.loginsdk.net.FailedEvent;
import com.example.loginsdk.net.LoginImpl;
import com.example.loginsdk.net.MessageType;
import com.example.loginsdk.util.AppUtils;
import com.example.loginsdk.util.Constant;
import com.example.loginsdk.util.MD5Util;
import com.example.loginsdk.util.PreferenceConstants;
import com.example.loginsdk.util.PreferenceUtils;
import com.example.loginsdk.util.RegularUtils;
import com.example.loginsdk.util.ResUtils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class LoginFragment extends BaseFragment {
	private OnLoginFragmentListener onLoginFragmentListener;

	public void setOnLoginFragmentListener(OnLoginFragmentListener onLoginFragmentListener) {
		this.onLoginFragmentListener = onLoginFragmentListener;
	}

	private static LoginCallback mLoginCallback;

	private View rootView;
	private Button login;
	private ImageView iv_close;

	private TextView quick_login, regist, retrieve_password;

	private EditText userNameEdit, passwordEdit;

	private ImageView qq_login,wx_login;

	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI mWeixinAPI;

	//第三方qq登录
	public static Tencent mTencent;
	private BaseUiListener loginListener;

	private int orientation;

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
		this.mLoginCallback = AccountManager.getLoginCallback();

		if(this.getArguments() != null) {
			Bundle data = getArguments();
			this.orientation = data.getInt("orientation");
		}

		
		
		if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			rootView = inflater.inflate(ResUtils.getLayout("yyh_fragment_login_l"), container, false);
		} else{
			rootView = inflater.inflate(ResUtils.getLayout( "yyh_fragment_login") ,container, false);
		}
		login = (Button) rootView.findViewById(ResUtils.getId("login"));
		iv_close = (ImageView) rootView.findViewById(ResUtils.getId("iv_close"));
		quick_login = (TextView) rootView.findViewById(ResUtils.getId("quick_login"));
		regist = (TextView) rootView.findViewById(ResUtils.getId("regist"));
		retrieve_password = (TextView) rootView.findViewById(ResUtils.getId("retrieve_password"));

		
		
		userNameEdit = (EditText) rootView.findViewById(ResUtils.getId("username"));
		passwordEdit = (EditText) rootView.findViewById(ResUtils.getId("password"));

		wx_login = (ImageView) rootView.findViewById(ResUtils.getId("wx_login"));
		qq_login = (ImageView) rootView.findViewById(ResUtils.getId("qq_login"));

		String account = PreferenceUtils.getPrefString(getActivity(), PreferenceConstants.ACCOUNT,"");
		String password = PreferenceUtils.getPrefString(getActivity(), PreferenceConstants.PASSWORD,"");

		if(!TextUtils.isEmpty(account)){
			userNameEdit.setText(account);
		}
		if(!TextUtils.isEmpty(password)){
			passwordEdit.setText(password);
		}

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
					showLoginDialog("登录中...");
					PreferenceUtils.setPrefString(getActivity(), PreferenceConstants.ACCOUNT,username);
					PreferenceUtils.setPrefString(getActivity(), PreferenceConstants.PASSWORD,password);
					password = MD5Util.md5(password);
					LoginImpl.getInstance(getActivity()).login(new LoginRequest(username,password));
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

		wx_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mWeixinAPI == null) {
					mWeixinAPI = WXAPIFactory.createWXAPI(getActivity(), Constant.WX_APP_ID, false);
				}

				if (!mWeixinAPI.isWXAppInstalled()) {
					//提醒用户没有按照微信
					return;
				}
				mWeixinAPI.registerApp(Constant.WX_APP_ID);

				SendAuth.Req req = new SendAuth.Req();
				req.scope = "snsapi_userinfo";
				req.state = "none";
				mWeixinAPI.sendReq(req);
			}
		});

		qq_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mTencent == null) {
					mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getActivity());
				}
				if(loginListener == null){
					loginListener = new BaseUiListener(getActivity(),mTencent);
				}
				if (!mTencent.isSessionValid()) {
					mTencent.login(getActivity(), "all", loginListener);
				} else {
					mTencent.logout(getActivity());
				}
			}
		});
	}

	public static LoginFragment newInstance(int var0) {
		LoginFragment var1 = new LoginFragment();
		Bundle var2;
		(var2 = new Bundle()).putInt("orientation", var0);
		var1.setArguments(var2);
		return var1;
	}

	@Override
	public void onEventMainThread(Object event) {
		super.onEventMainThread(event);
		if(event instanceof JsonResult){
			dismissDialog();
			JsonResult jsonResult = (JsonResult) event;
			if(jsonResult.getMessage().equals("login")){
				mLoginCallback.onLoginSuccess(getActivity(),jsonResult.getData());
				getActivity().finish();
			}
			if(jsonResult.getMessage().equals("wx_login")){
				showDialog("获取微信信息...");
				LoginImpl.getInstance(getActivity()).getWXUserInfo((String) jsonResult.getData());
			}

			if (jsonResult.getMessage().equals("thirdLogin")) {
				mLoginCallback.onLoginSuccess(getActivity(), jsonResult.getData());
				getActivity().finish();
			}
		}
		if(event instanceof WXUserInfo){
			showDialog("微信登录中...");
			WXUserInfo userInfo = (WXUserInfo) event;
//			T.showShort(getActivity(),userInfo.getNickname() +" "+userInfo.getOpenid());
			LoginImpl.getInstance(getActivity()).thirdLogin(userInfo.getNickname(),userInfo.getUnionid());
		}
		if(event instanceof FailedEvent){
			dismissDialog();
			int type = ((FailedEvent) event).getType();
			String message = (String) ((FailedEvent) event).getObject();
			if(type == MessageType.LOGIN){
				PreferenceUtils.setPrefString(getActivity(), PreferenceConstants.ACCOUNT,"");
				PreferenceUtils.setPrefString(getActivity(), PreferenceConstants.PASSWORD,"");
			}
			mLoginCallback.onLoginError(getActivity(),message);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
			showDialog("QQ登录中");
			Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
