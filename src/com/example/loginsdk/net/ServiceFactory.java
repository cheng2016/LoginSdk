package com.example.loginsdk.net;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;
import com.example.loginsdk.util.NetUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Michael Smith on 2016/7/21.
 */

public class ServiceFactory {
	private final static String TAG = "ServiceFactory";

    private final static String WEIXIN = "-----BEGIN CERTIFICATE-----\n" +
            "MIIHqDCCBpCgAwIBAgIQBqNJx7yh4mP8h5BwE0YyiTANBgkqhkiG9w0BAQsFADBE\n" +
            "MQswCQYDVQQGEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UEAxMU\n" +
            "R2VvVHJ1c3QgU1NMIENBIC0gRzMwHhcNMTYwNDAxMDAwMDAwWhcNMTgwMTI4MjM1\n" +
            "OTU5WjCBmTELMAkGA1UEBhMCQ04xEjAQBgNVBAgTCUd1YW5nZG9uZzERMA8GA1UE\n" +
            "BxQIU2hlbnpoZW4xOjA4BgNVBAoUMVNoZW56aGVuIFRlbmNlbnQgQ29tcHV0ZXIg\n" +
            "U3lzdGVtcyBDb21wYW55IExpbWl0ZWQxDDAKBgNVBAsUA1ImRDEZMBcGA1UEAxQQ\n" +
            "bXAud2VpeGluLnFxLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
            "ALzUvYlLdW2kf9yuF4k51+Jnn8R1eUdvo3ifE6+1FkGRS+NoZDF1rupuQy52NLQG\n" +
            "2PQjfJ8HsigMVxSscRrbxqgpMOqLIvh+xgq+rIetPWPLL0FuqX8NRspksVuTjs8v\n" +
            "EF1riF/DuuFq0ZCQzE+YBS+i4Cd5rgHCG83FJZ6kUdQQ+yl8k4Pqa2QCDfSxuUUd\n" +
            "92PQJfdDGxQbZLdXBodBecIqSBLBrvV2v5JhMRJ/IYEpEzKeUayt4rZW4YWC0A3F\n" +
            "h88rOrSCZ4+kg+PdO5ql2TmtehoEptCfGAaXYcS/kz/ioywZw3ctF02yUQE8oqjH\n" +
            "6mjyTT1AYpV2mVMsriydfjECAwEAAaOCBD4wggQ6MIIBMwYDVR0RBIIBKjCCASaC\n" +
            "F2xvbmcub3Blbi53ZWl4aW4ucXEuY29tghJvcGVuLndlaXhpbi5xcS5jb22CFWhr\n" +
            "Lm9wZW4ud2VpeGluLnFxLmNvbYITbXAud2VpeGluYnJpZGdlLmNvbYISZ2FtZS53\n" +
            "ZWl4aW4ucXEuY29tghNzei5tcC53ZWl4aW4ucXEuY29tghRzaC5hcGkud2VpeGlu\n" +
            "LnFxLmNvbYIVc3oub3Blbi53ZWl4aW4ucXEuY29tghNoay5tcC53ZWl4aW4ucXEu\n" +
            "Y29tgg9hLndlaXhpbi5xcS5jb22CFHN6LmFwaS53ZWl4aW4ucXEuY29tghFhcGku\n" +
            "d2VpeGluLnFxLmNvbYIUaGsuYXBpLndlaXhpbi5xcS5jb22CEG1wLndlaXhpbi5x\n" +
            "cS5jb20wCQYDVR0TBAIwADAOBgNVHQ8BAf8EBAMCBaAwKwYDVR0fBCQwIjAgoB6g\n" +
            "HIYaaHR0cDovL2duLnN5bWNiLmNvbS9nbi5jcmwwgZ0GA1UdIASBlTCBkjCBjwYG\n" +
            "Z4EMAQICMIGEMD8GCCsGAQUFBwIBFjNodHRwczovL3d3dy5nZW90cnVzdC5jb20v\n" +
            "cmVzb3VyY2VzL3JlcG9zaXRvcnkvbGVnYWwwQQYIKwYBBQUHAgIwNQwzaHR0cHM6\n" +
            "Ly93d3cuZ2VvdHJ1c3QuY29tL3Jlc291cmNlcy9yZXBvc2l0b3J5L2xlZ2FsMB0G\n" +
            "A1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAfBgNVHSMEGDAWgBTSb/eW9IU/\n" +
            "cjwwfSPahXibo3xafDBXBggrBgEFBQcBAQRLMEkwHwYIKwYBBQUHMAGGE2h0dHA6\n" +
            "Ly9nbi5zeW1jZC5jb20wJgYIKwYBBQUHMAKGGmh0dHA6Ly9nbi5zeW1jYi5jb20v\n" +
            "Z24uY3J0MIIBfgYKKwYBBAHWeQIEAgSCAW4EggFqAWgAdgDd6x0reg1PpiCLga2B\n" +
            "aHB+Lo6dAdVciI09EcTNtuy+zAAAAVPPsRPWAAAEAwBHMEUCIEpHYsY1PSzqxTQR\n" +
            "hXX8Dm7KPHOoupHKPM/C+Zoyz565AiEA1eo45O8i9sNx4hp+lI0PjEQEyUO6yUyH\n" +
            "KFxQymaMGBUAdQCkuQmQtBhYFIe7E6LMZ3AKPDWYBPkb37jjd80OyA3cEAAAAVPP\n" +
            "sRQbAAAEAwBGMEQCIBtawmqsd59E+ix03ZJ2f0HPMrWMGS9IManYi9kZfCkHAiA7\n" +
            "uInuX0V3cyqWg8NDwrW7IEQQiipauhq7qFtWnJ7qHQB3AGj2mPgfZIK+OozuuSgd\n" +
            "TPxxUV1nk9RE0QpnrLtPT/vEAAABU8+xFBwAAAQDAEgwRgIhAIItwFOzbWaTirf8\n" +
            "j4cWuDK4gC39ILLu1HeG/bh7lEZwAiEAtoypAS0bx9DeCDdOerDwAUzu4LVjlTF2\n" +
            "goBnTyvtxOYwDQYJKoZIhvcNAQELBQADggEBAAinkTjQgWWj6pGKB2E1f1B/Xpml\n" +
            "cqQFY9sESVXkXn96H4dvSRGG+oeeWkuSIzOnCTQbKusIRLRm0TFMmcje0QTKg7yv\n" +
            "ibvd389P976onOJkiocZQW+PYhQOpm3yCFs9ckmoEddLueLrT9weLteA9k2wpr3h\n" +
            "Tj78d4j+nK+IRfmwm5spxuzbHvl52d8B+nA+laNBUveRSbodx8xpI6qRNy0RtoYq\n" +
            "aGWLT/x+nesbtddUgkosy9NWVMUiPu2yFjQXmNqCbWaef3kaDw0LK9SXrYZUFoDS\n" +
            "w3aEAgULiO7qXPJEWGfVEP+9ZlcA1kRIYw8OLK58x0mt+TKP7PZGwMoSzpk=\n" +
            "-----END CERTIFICATE-----\n";

    private ServiceFactory() {
    }

    public static <T> T createRetrofit2Service(final Class<T> service,String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(service);
    }

    public  static <T> T createRetrofit2RxJavaService(final Class<T> service,String baseUrl,Context context) {
    	Retrofit retrofit = new Retrofit.Builder()
                .client(getCacheOkHttpClient(context))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(service);
    }

    public  static <T> T createSSLService(final Class<T> service,String baseUrl,Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getSSLClient(context))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(service);
    }

    private static OkHttpClient getCacheOkHttpClient(final Context context) {
        //设置缓存路径
        final File httpCacheDirectory = new File(context.getCacheDir(), "okhttpCache");

        Log.i(TAG, httpCacheDirectory.getAbsolutePath());
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);   //缓存可用大小为10M


        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //获取网络状态
                int netWorkState = NetUtils.getNetworkState(context);
                //无网络，请求强制使用缓存
                if (netWorkState == NetUtils.NETWORK_NONE) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response originalResponse = chain.proceed(request);

                switch (netWorkState) {
                    case NetUtils.NETWORK_MOBILE://mobile network 情况下缓存一分钟
                        int maxAge = 60;
                        return originalResponse.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();

                    case NetUtils.NETWORK_WIFI://wifi network 情况下不使用缓存
                        maxAge = 0;
                        return originalResponse.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();

                    case NetUtils.NETWORK_NONE://none network 情况下离线缓存4周
                        int maxStale = 60 * 60 * 24 * 4 * 7;
                        return originalResponse.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                    default:
                        throw new IllegalStateException("network state  is Erro!");
                }
            }
        };

        return new OkHttpClient.Builder()
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                //设置拦截器，显示日志信息
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
//                .authenticator(new TokenAuthenticator())
                .build();
    }

    public static OkHttpClient getSSLClient(Context context){
        OkHttpClient.Builder builder =  new OkHttpClient.Builder()
                                            .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                                            .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                                            .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                                            .addInterceptor(httpLoggingInterceptor);
        HttpsUtils.SSLParams sslParams = setInputStream( new Buffer().writeUtf8(WEIXIN).inputStream());

//        try {
//            sslParams = setInputStream(context.getAssets().open("mp.weixin.qq.com.crt"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        return builder.build();
    }

    private static HttpsUtils.SSLParams setInputStream(InputStream... inputStream){
        return HttpsUtils.getSslSocketFactory(null, null, inputStream);
    }

    private final static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


    public OkHttpClient.Builder setCertificates(OkHttpClient.Builder mOkHttpClient,InputStream... certificates)
    {
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try
                {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)
                {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            return  mOkHttpClient.sslSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return  mOkHttpClient;
    }
}
