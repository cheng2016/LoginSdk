package com.example.loginsdk.net;

import java.io.IOException;

import com.example.loginsdk.bean.RefreshRequest;
import com.example.loginsdk.bean.Token;
import com.example.loginsdk.util.PreferenceConstants;
import com.example.loginsdk.util.PreferenceUtils;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

/**
 * Created by Michael Smith on 2016/7/30.
 */

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        //取出本地的refreshToken
 /*       String refreshToken = PreferenceUtils.getPrefString(RxApplication.getInstance(), PreferenceConstants.REFRESH_TOKEN,"");

        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        ServiceApi service = ServiceFactory.createRetrofit2Service(ServiceApi.class,ServiceApi.baseurl);
        Call<Token> call = service.refresh(new RefreshRequest(refreshToken));

        //要用retrofit的同步方式
        Token token = call.execute().body();

        PreferenceUtils.setPrefString(RxApplication.getInstance(), PreferenceConstants.REFRESH_TOKEN,token.getRefresh_token());

        return response.request().newBuilder()
                .header("X-ZUMO-AUTH", token.getAccess_token())
                .build();*/
        return null;
    }
}
