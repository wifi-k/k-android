package net.treebear.kwifimanager.http;


import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.util.SharedPreferencesUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 13265969699
 * 123456abc
 *
 * @author Administrator
 */

public class WiFiHttpClient {
    private static WiFiHttpClient mRetrofitHttp;
    private String baseUrl = Config.Urls.ROUTER_BASE_URL;
    private Retrofit retrofit;
    private static String apiToken = "";

    private WiFiHttpClient() {

    }

    public static WiFiHttpClient getInstance() {
        synchronized (WiFiHttpClient.class) {
            if (mRetrofitHttp == null) {
                mRetrofitHttp = new WiFiHttpClient();
            }
        }
        return mRetrofitHttp;
    }

    /**
     * 方便测试期间后门更改 IP : port
     */
    public static void updateClient() {
        getInstance().retrofit = null;
        getInstance().initRetrofit();
    }

    public static void updataApiToken(String token) {
        apiToken = token;
        updateClient();
    }

    private void initRetrofit() {
        if (retrofit == null) {
            //缓存
            File cacheFile = new File(MyApplication.getAppContext().getCacheDir(), "cache");
            //100Mb
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
            //增加头部信息
            Interceptor headerInterceptor = chain -> {
                Request.Builder builder = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Api-Token", apiToken)
                        .addHeader("charset", "utf-8")
                        .addHeader("Api-Version", BuildConfig.VERSION_NAME);

                String cookies[] = ((String) SharedPreferencesUtil.getParam(MyApplication.getAppContext(), "cookies", "")).split("-");
                for (String cookie : cookies) {
                    builder.addHeader("Cookie", cookie);
                }

                Request request = builder.build();
                return chain.proceed(request);
            };

            //增加cookie信息
//            Interceptor cookieInterceptor = chain -> {
//                okhttp3.Response originalResponse = chain.proceed(chain.request());
//                if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//                    StringBuilder sb = new StringBuilder();
//                    for (String cookie : originalResponse.headers("Set-Cookie")) {
//                        sb.append(cookie).append("-");
//                    }
//                    SharedPreferencesUtil.setParam(MyApplication.getAppContext(), "cookies", sb.length() > 0 ? sb.subSequence(0, sb.length() - 1).toString() : "");
//                }
//
//                return originalResponse;
//            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.addInterceptor(headerInterceptor);
//            builder.addInterceptor(cookieInterceptor);
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            builder.cache(cache);

            retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();
        }
    }

    public WiFiApi getApiService() {
        initRetrofit();
        return retrofit.create(WiFiApi.class);
    }

    private String getBaseUrl() {
        return baseUrl == null ? Config.Urls.SERVER_BASE_URL : baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofit = null;
    }
}