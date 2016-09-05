package com.example.loginsdk.listener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mitnick.cheng on 2016/9/2.
 */

public final class BaseUiListener implements IUiListener {
    private final static String TAG = "BaseUiListener";

    private Context context;
    private Tencent tencent;

    //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
    public BaseUiListener(Context context) {
        this.context = context;
    }

    public BaseUiListener(Context context,Tencent tencent) {
        this.context = context;
        this.tencent = tencent;
    }

    @Override
    public void onComplete(Object response) {
        JSONObject object = (JSONObject) response;
        doComplete(object);
    }

    //在这里可以做一些登录成功的处理
    protected void doComplete(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                tencent.setAccessToken(token, expires);
                tencent.setOpenId(openId);
            }
            UserInfo userInfo = new UserInfo(context, tencent.getQQToken());
            userInfo.getUserInfo(new UserInfoUiLIstener(context,openId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //在这里可以做登录失败的处理
    @Override
    public void onError(UiError e) {
        Log.i(TAG, "onError");
    }

    //在这里可以做登录被取消的处理
    @Override
    public void onCancel() {
        Log.i(TAG, "onCancel");
    }
}

