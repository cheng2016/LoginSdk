package com.example.loginsdk.net;

import org.greenrobot.eventbus.EventBus;

import com.example.loginsdk.bean.JsonResult;
import com.example.loginsdk.bean.LoginRequest;
import com.example.loginsdk.bean.User;
import com.example.loginsdk.bean.UserRequest;
import com.example.loginsdk.util.LogUtils;

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

    public LoginImpl() {
    }

    public LoginApi getApiClient() {
        if (mApiClient == null) {
            synchronized (this) {
                LogUtils.i(TAG, "LoginApi.newInstance() excute ");
                mApiClient = ServiceFactory.createRetrofit2RxJavaService(LoginApi.class,LoginApi.baseurl);
            }
        }
        return mApiClient;
    }

    //获取唯一单列
    public static LoginImpl getInstance() {
        if (sInstance == null) {
            synchronized (LoginImpl.class) {
                LogUtils.i(TAG, "LoginImpl.newInstance() excute ");
                sInstance = new LoginImpl();
            }
        }
        return sInstance;
    }

    private final void postEvent(Object object) {
        EventBus.getDefault().post(object);
    }

    public void login(LoginRequest loginRequest){
        getApiClient().login(loginRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResult<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        postEvent(new FailedEvent(MessageType.LOGIN ,throwable.getMessage()));
                    }

                    @Override
                    public void onNext(JsonResult<User> jsonResult) {
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

}
