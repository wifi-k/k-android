package net.treebear.kwifimanager.http;


import android.util.ArrayMap;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.wifi.model.WiFiSettingProxyModel;
import net.treebear.kwifimanager.util.RequestBodyUtils;
import net.treebear.kwifimanager.util.SecurityUtils;
import net.treebear.kwifimanager.util.TLog;

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
    private static WiFiSettingProxyModel wifiProxyClient;
    private String baseUrl = Config.Urls.ROUTER_BASE_URL;
    private Retrofit retrofit;
    private static String apiToken = "";
    private static boolean needLogin = true;
    private static boolean isLogin_ing = false;

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
                        .addHeader("Accept-Encoding", "utf-8")
                        .addHeader("Api-Version", "2019.3.1");
                Request request = builder.build();
                return chain.proceed(request);
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(160, TimeUnit.SECONDS);
            builder.readTimeout(160, TimeUnit.SECONDS);
            builder.addInterceptor(headerInterceptor);
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

    /**
     * 小K设备下线，再次上线需要重新登录
     */
    public static void xiaokOffline() {
        needLogin = true;
    }

    /**
     * 小K设备上线，尝试登录
     */
    public static void xiaokOnline() {
        tryToSignInWifi(null);
    }

    /**
     * 便于各个界面调用WiFi登录
     * 内部处理token更新
     */
    public static void tryToSignInWifi(IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        // 保证全局单次连接wifi只登录一次
        if (!needLogin) {
            TLog.w("Current device has logged in !");
            return;
        }
        if (isLogin_ing) {
            TLog.w("Current device is login ing !");
            return;
        }
        if (wifiProxyClient == null) {
            wifiProxyClient = new WiFiSettingProxyModel();
        }
        isLogin_ing = true;
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put(Keys.NAME, "admin");
        map.put(Keys.PASSWD_WIFI, SecurityUtils.md5(Config.Text.XIAO_K_WIFI_PASSOWRD));
        wifiProxyClient.appLogin(RequestBodyUtils.convert(map), new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                TLog.e("OkHttp", "WiFi login success !" + TLog.valueOf(resultData));
//                ToastUtils.showShort(TLog.valueOf(resultData.getData().toString()));
                if (resultData.getData() != null) {
                    apiToken = resultData.getData().getToken();
                    updataApiToken(apiToken);
                    MyApplication.getAppContext().getDeviceInfo().setToken(apiToken);
                    needLogin = false;
//                    if (callBack != null) {
//                        callBack.onSuccess(resultData);
//                    }
                    isLogin_ing = false;
                    getDeviceSerialId(callBack);
//                    getDeviceOnlineStatus();
                }
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                TLog.e("OkHttp", "WiFi login failed , code : " + resultCode + ", message : " + resultMsg);
                isLogin_ing = false;
                if (callBack != null) {
                    callBack.onFailed(resultMsg, resultCode);
                }
            }
        });
    }

    private static void getDeviceSerialId(IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        wifiProxyClient.getNodeInfo(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                TLog.i(resultData);
                if (resultData != null && resultData.getData() != null) {
//                    MyApplication.getAppContext().getDeviceInfo().setId(resultData.getData().getId());
                    MyApplication.getAppContext().saveDeviceInfo(resultData.getData());
                    if (callBack!=null) {
                        callBack.onSuccess(resultData);
                    }
                }
//                getDeviceOnlineStatus();
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                TLog.e("OkHttp", "WiFi login failed , code : " + resultCode + ", message : " + resultMsg);
                if (callBack != null) {
                    callBack.onFailed(resultMsg, resultCode);
                }
            }
        });
    }

    private static void getDeviceOnlineStatus() {
        wifiProxyClient.queryNetStatus(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                TLog.i("getDeviceOnlineStatus ： " + TLog.valueOf(resultData));
                MyApplication.getAppContext().getDeviceInfo().setConnect(true);
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                TLog.e("OkHttp", "WiFi login failed , code : " + resultCode + ", message : " + resultMsg);
            }
        });
    }

    private String getBaseUrl() {
        return baseUrl == null ? Config.Urls.ROUTER_BASE_URL : baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofit = null;
    }
}
