package com.example.loginsdk.wxapi;


import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.util.LogUtils;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends FragmentActivity implements IWXAPIEventHandler{
    private IWXAPI api;
    private BaseResp resp = null;

    public final static String WX_APP_ID = "wx18f2bcc7e49b6dd8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("WXEntryActivity","onCreate()");
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        api.registerApp(WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        LogUtils.i("WXEntryActivity","onReq()");
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        LogUtils.i("WXEntryActivity","onResp()");
        String result = "";
        if (resp != null) {
            this.resp = resp;
        }
        switch(this.resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result ="微信登录成功";
                EventBus.getDefault().post(new JsonResult(200,"wx_login",result));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "登录取消";
                EventBus.getDefault().post(new JsonResult(500,result,null));
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "登录被拒绝";
                EventBus.getDefault().post(new JsonResult(500,result,null));
                break;
            default:
                result = "登录返回";
                EventBus.getDefault().post(new JsonResult(500,result,null));
                break;
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.i("WXEntryActivity","onNewIntent()");
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }
}