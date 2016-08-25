package com.example.loginsdk.net;

import com.example.loginsdk.bean.JsonResult;
import com.example.loginsdk.bean.LoginRequest;
import com.example.loginsdk.bean.User;
import com.example.loginsdk.bean.UserRequest;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mitnick.cheng on 2016/8/18.
 */

public interface LoginApi {
    public static final String baseurl = "http://sdk.stg.ftxgame.com:8880/web-ssm/";

    public final static String REGIST = "regist";
    public final static String RETRIEVE = "retrieve";

    @POST("user/login")
    Observable<JsonResult<User>> login(@Body LoginRequest loginRequest);

    @POST("user/getProfile")
    Observable<JsonResult<User>> getProfile(@Header("X-ZUMO-AUTH") String accessToken);

    @POST("user/code")
    Observable<JsonResult> getCode(@Query("phone") String phone, @Query("type") String type);

    @POST("user/regist")
    Observable<JsonResult> regist(@Header("X-ZUMO-AUTH") String accessToken, @Body UserRequest userRequest);

    @POST("user/retrieve")
    Observable<JsonResult> retrieve(@Header("X-ZUMO-AUTH") String accessToken, @Body UserRequest userRequest);
}
