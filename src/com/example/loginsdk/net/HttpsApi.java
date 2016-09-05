package com.example.loginsdk.net;

import com.example.loginsdk.bean.response.WXToken;
import com.example.loginsdk.bean.response.WXUserInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mitnick.cheng on 2016/9/2.
 */

public interface HttpsApi {
    public static final String baseurl = "https://api.weixin.qq.com/";

    @GET("sns/oauth2/access_token")
    Observable<WXToken> getWXToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String authorization_code);

    @GET("sns/userinfo")
    Observable<WXUserInfo> getWXUserInfo(@Query("access_token") String access_token, @Query("openid") String openid);
}
