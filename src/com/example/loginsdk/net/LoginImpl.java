package com.example.loginsdk.net;

import org.greenrobot.eventbus.EventBus;

import com.example.loginsdk.bean.Account;
import com.example.loginsdk.bean.SecurityRequest;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.bean.request.LoginRequest;
import com.example.loginsdk.bean.request.UserRequest;
import com.example.loginsdk.bean.response.WXToken;
import com.example.loginsdk.bean.response.WXUserInfo;
import com.example.loginsdk.util.Constant;
import com.example.loginsdk.util.L;

import android.content.Context;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mitnick.cheng on 2016/8/19.
 */

public class LoginImpl {
    private final static String TAG = "LoginImpl";

    static volatile LoginImpl sInstance;
    static volatile LoginApi mApiClient;

    static volatile HttpsApi mHttpsClient;
    
    private Context mContext;

    public LoginImpl(Context context) {
    	this.mContext = context;
    }

    //获取唯一单列
    public static LoginImpl getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LoginImpl.class) {
                L.i(TAG, "LoginImpl.newInstance() excute ");
                sInstance = new LoginImpl(context);
            }
        }
        return sInstance;
    }

    private LoginApi getApiClient() {
        if (mApiClient == null) {
            synchronized (this) {
                L.i(TAG, "LoginApi.newInstance() excute ");
                mApiClient = ServiceFactory.createRetrofit2RxJavaService(LoginApi.class,LoginApi.baseurl,mContext);
            }
        }
        return mApiClient;
    }

    private HttpsApi getHttpsClient() {
        if (mHttpsClient == null) {
            synchronized (this) {
                L.i(TAG, "LoginApi.newInstance() excute ");
                mHttpsClient = ServiceFactory.createSSLService(HttpsApi.class,HttpsApi.baseurl,mContext);
            }
        }
        return mHttpsClient;
    }

    private final void postEvent(Object object) {
        EventBus.getDefault().post(object);
    }

    public void login(LoginRequest loginRequest){
        getApiClient().login(loginRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult<Account>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.LOGIN ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult<Account> jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.LOGIN ,jsonResult.getMessage()));
                        }
                    }
                });
    }

    public void getCode(String phone,String type){
        getApiClient().getCode(phone,type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.GETCODE ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.GETCODE ,jsonResult.getMessage()));
                        }
                    }
                });
    }

    public void regist(String token,UserRequest userRequest){
        getApiClient().regist(token,userRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.REGIST ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.REGIST ,jsonResult.getMessage()));
                        }
                    }
                });
    }

    public void retrieve(String token,UserRequest userRequest){
        getApiClient().retrieve(token,userRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.RETRIEVE ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.RETRIEVE ,jsonResult.getMessage()));
                        }
                    }
                });
    }

    public void thirdLogin(String userName,String openId){
        getApiClient().thirdLogin(userName,openId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult<Account>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.THIRDLOGIN ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult<Account> jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.THIRDLOGIN ,jsonResult.getMessage()));
                        }
                    }
                });
    }

    public void checkLogin(Account account){
        getApiClient().checkLogin(account)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.CHECKLOGIN ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.CHECKLOGIN ,jsonResult.getMessage()));
                        }
                    }
                });
    }

    public void getWXToken(String code){
        getHttpsClient().getWXToken(Constant.WX_APP_ID, Constant.WX_APP_SECRET,code,Constant.WX_GRANT_TYPE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WXToken>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.WXTOKEN ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(WXToken wxToken) {
                        postEvent(wxToken);
                    }
                });
    }

    public void getWXUserInfo(String code){
        getHttpsClient().getWXToken(Constant.WX_APP_ID,Constant.WX_APP_SECRET,code,Constant.WX_GRANT_TYPE)
                .flatMap(new Func1<WXToken, Observable<WXUserInfo>>() {
                    @Override
                    public Observable<WXUserInfo> call(WXToken wxToken) {

                        return getHttpsClient().getWXUserInfo(wxToken.getAccess_token(),wxToken.getOpenid());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WXUserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.WXUSERINFO ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(WXUserInfo wxUserInfo) {
                        postEvent(wxUserInfo);
                    }
                });
    }

    public void securityLogin(SecurityRequest securityRequest){
        getApiClient().securityLogin(securityRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult<Account>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.LOGIN ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult<Account> jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.LOGIN ,jsonResult.getMessage()));
                        }
                    }
                });
    }
}
