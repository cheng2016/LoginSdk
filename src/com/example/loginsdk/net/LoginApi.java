package com.example.loginsdk.net;

import com.example.loginsdk.bean.Account;
import com.example.loginsdk.bean.response.JsonResult;
import com.example.loginsdk.bean.request.LoginRequest;
import com.example.loginsdk.bean.response.User;
import com.example.loginsdk.bean.request.UserRequest;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mitnick.cheng on 2016/8/18.
 */

public interface LoginApi {
//    public static final String baseurl = "http://sdk.stg.ftxgame.com:8880/web-ssm/";
//    public static final String baseurl = "http://192.168.12.22:8080/LoginServer/";
    public static final String baseurl = "http://sdk.stg.ftxgame.com:8880/LoginServer/";

    public final static String REGIST = "regist";
    public final static String RETRIEVE = "retrieve";

    @POST("user/login")
    Observable<JsonResult<Account>> login(@Body LoginRequest loginRequest);

    @POST("user/getProfile")
    Observable<JsonResult<User>> getProfile(@Header("X-ZUMO-AUTH") String accessToken);

    @POST("user/getCode")
    Observable<JsonResult> getCode(@Query("phone") String phone, @Query("type") String type);

    @POST("user/regist")
    Observable<JsonResult> regist(@Header("X-ZUMO-AUTH") String accessToken, @Body UserRequest userRequest);

    @POST("user/retrieve")
    Observable<JsonResult> retrieve(@Header("X-ZUMO-AUTH") String accessToken, @Body UserRequest userRequest);

    @POST("user/checkLogin")
    Observable<JsonResult> checkLogin(@Body Account account);

    @POST("user/qqLogin")
    Observable<JsonResult<Account>> qqLogin(@Query("name") String userName);
}
