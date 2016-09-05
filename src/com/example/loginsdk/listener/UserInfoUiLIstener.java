package com.example.loginsdk.listener;

import android.content.Context;
import android.util.Log;

import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.net.LoginImpl;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mitnick.cheng on 2016/9/2.
 */

public final class UserInfoUiLIstener implements IUiListener {
    private final static String TAG = "UserInfoUiLIstener";

    private Context context;
    private String openId;

    public UserInfoUiLIstener(Context context,String openId) {
        this.context = context;
        this.openId = openId;
    }

    @Override
    public void onComplete(Object o) {
        doComplete((JSONObject) o);
    }

    protected void doComplete(JSONObject response) {
        try {
            if (response.has("nickname")) {
                try {
                    Log.i(TAG, response.getString("nickname") +" "+ openId);
                    LoginImpl.getInstance(context).thirdLogin(response.getString("nickname"),openId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Log.i(TAG, "onError");
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "onCancel");
    }
}
