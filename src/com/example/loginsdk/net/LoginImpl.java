package com.example.loginsdk.net;

import org.greenrobot.eventbus.EventBus;

import com.example.loginsdk.bean.Account;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.bean.request.LoginRequest;
import com.example.loginsdk.bean.request.UserRequest;
import com.example.loginsdk.util.LogUtils;

import android.content.Context;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mitnick.cheng on 2016/8/19.
 */

public class LoginImpl {
    private final static String TAG = "LoginImpl";

    static volatile LoginImpl sInstance;
    static volatile LoginApi mApiClient;
    
    private Context mContext;

    public LoginImpl(Context context) {
    	this.mContext = context;
    }

    private LoginApi getApiClient() {
        if (mApiClient == null) {
            synchronized (this) {
                LogUtils.i(TAG, "LoginApi.newInstance() excute ");
                mApiClient = ServiceFactory.createRetrofit2RxJavaService(LoginApi.class,LoginApi.baseurl,mContext);
            }
        }
        return mApiClient;
    }

    //获取唯一单列
    public static LoginImpl getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LoginImpl.class) {
                LogUtils.i(TAG, "LoginImpl.newInstance() excute ");
                sInstance = new LoginImpl(context);
            }
        }
        return sInstance;
    }

    private LoginApi getHttpsClient() {
        if (mApiClient == null) {
            synchronized (this) {
                LogUtils.i(TAG, "LoginApi.newInstance() excute ");
                mApiClient = ServiceFactory.createSSLService(LoginApi.class,"https://api.weixin.qq.com/",mContext);
            }
        }
        return mApiClient;
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

    public void qqLogin(String userName){
        getApiClient().qqLogin(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult<Account>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.QQLOGIN ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult<Account> jsonResult) {
                        if(jsonResult.isSuccess()){
                            postEvent(jsonResult);
                        }else{
                            postEvent(new FailedEvent(MessageType.QQLOGIN ,jsonResult.getMessage()));
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
}
