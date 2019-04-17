package cn.treebear.kwifimanager.http;


import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.treebear.kwifimanager.BuildConfig;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.config.Config;
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

public class HttpClient {
    private static HttpClient mRetrofitHttp;
    private static String apiToken = "";
    private String baseUrl = Config.Urls.SERVER_BASE_URL;
    private Retrofit retrofit;

    private HttpClient() {

    }

    public static HttpClient getInstance() {
        synchronized (HttpClient.class) {
            if (mRetrofitHttp == null) {
                mRetrofitHttp = new HttpClient();
            }
        }
        return mRetrofitHttp;
    }

    /**
     * 方便测试期间后门更改 IP : port
     */
    public static void updateUrl() {
        getInstance().retrofit = null;
        getInstance().initRetrofit();
    }

    public static void updateApiToken(String token) {
        getInstance().retrofit = null;
        apiToken = token;
        getInstance().initRetrofit();
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
//                        .addHeader("Accept", "application/json")
//                        .addHeader("charset", "utf-8")
                        .addHeader("Api-Version", BuildConfig.VERSION_NAME);
                Request request = builder.build();
                return chain.proceed(request);
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.addInterceptor(headerInterceptor);
//            if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
            builder.addInterceptor(loggingInterceptor);
//            }
            builder.cache(cache);

            retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();
        }
    }

    public HttpService getApiService() {
        initRetrofit();
        return retrofit.create(HttpService.class);
    }

    private String getBaseUrl() {
        return baseUrl == null ? Config.Urls.SERVER_BASE_URL : baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofit = null;
    }
}
