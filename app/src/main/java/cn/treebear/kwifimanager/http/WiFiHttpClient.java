package cn.treebear.kwifimanager.http;


import android.util.ArrayMap;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.wifi.model.WiFiSettingProxyModel;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.RequestBodyUtils;
import cn.treebear.kwifimanager.util.SecurityUtils;
import cn.treebear.kwifimanager.util.SharedPreferencesUtil;
import cn.treebear.kwifimanager.util.TLog;
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
    private static WifiDeviceInfo wifiDeviceInfo;
    private static boolean needLogin = true;
    private WiFiSettingProxyModel wifiProxyClient;
    private String baseUrl = Config.Urls.ROUTER_BASE_URL;
    private Retrofit retrofit;
    private volatile String apiToken = "";

    private WiFiHttpClient() {

    }

    public static WiFiHttpClient getInstance() {
        if (mRetrofitHttp == null) {
            synchronized (WiFiHttpClient.class) {
                if (mRetrofitHttp == null) {
                    mRetrofitHttp = new WiFiHttpClient();
                    wifiDeviceInfo = new WifiDeviceInfo();
                }
            }
        }
        return mRetrofitHttp;
    }

    public static boolean getNeedLogin() {
        if (wifiDeviceInfo == null) {
            return true;
        }
        return needLogin && !Check.hasContent(wifiDeviceInfo.getId());
    }

    public static WifiDeviceInfo getWifiDeviceInfo() {
        if (wifiDeviceInfo == null) {
            wifiDeviceInfo = new WifiDeviceInfo();
        }
        return wifiDeviceInfo;
    }

    public static void setWifiDeviceInfo(WifiDeviceInfo wifiDeviceInfo) {
        WiFiHttpClient.wifiDeviceInfo = wifiDeviceInfo;
    }

    public static String getApiToken() {
        if (wifiDeviceInfo != null) {
            return wifiDeviceInfo.getToken();
        }
        return getInstance().apiToken;
    }

    public static void updateApiToken(String token) {
        if (token == null) {
            token = "";
        }
        TLog.i("OkHttp", "----------***********---uuu--------");
        getInstance().apiToken = token;
        getInstance().retrofit = null;
        getInstance().initRetrofit();
    }

    /**
     * 小K设备下线，再次上线需要重新登录
     */
    public static void xiaokOffline() {
        needLogin = true;
        wifiDeviceInfo = null;
        wifiDeviceInfo = new WifiDeviceInfo();
        SharedPreferencesUtil.setParam(SharedPreferencesUtil.NODE_ID, "");
        getInstance().apiToken = "";
        getInstance().retrofit = null;
        getInstance().initRetrofit();
    }

    private static void toLogin(IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        if (getInstance().wifiProxyClient == null) {
            getInstance().wifiProxyClient = new WiFiSettingProxyModel();
        }
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put(Keys.NAME, "admin");
        map.put(Keys.PASSWD_WIFI, SecurityUtils.md5(Config.Text.XIAO_K_WIFI_PASSOWRD));
        getInstance().wifiProxyClient.appLogin(RequestBodyUtils.convert(map), new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                TLog.e("OkHttp", "WiFi login success !" + TLog.valueOf(resultData));
                if (wifiDeviceInfo == null) {
                    wifiDeviceInfo = new WifiDeviceInfo();
                }
                if (resultData.getData() != null) {
                    getInstance().apiToken = resultData.getData().getToken();
                    wifiDeviceInfo.setToken(getInstance().apiToken);
                    needLogin = false;
                    updateApiToken(getInstance().apiToken);
                    getDeviceSerialId(callBack);
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                TLog.e("OkHttp", "WiFi login failed , code : " + resultCode + ", message : " + resultMsg);
                needLogin = true;
                if (callBack != null) {
                    callBack.onFailed(resultData, resultMsg, resultCode);
                }
            }
        });
    }

    private static void getDeviceSerialId(IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        getInstance().wifiProxyClient.getNodeInfo(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                if (resultData != null && resultData.getData() != null) {
                    WifiDeviceInfo data = resultData.getData();
                    wifiDeviceInfo.setId(data.getId());
                    wifiDeviceInfo.setWan(data.getWan());
                    SharedPreferencesUtil.setParam(SharedPreferencesUtil.NODE_ID, data.getId());
                    resultData.setData(data);
                    if (callBack != null) {
                        callBack.onSuccess(resultData);
                    }
                    needLogin = false;
                }
            }

            @Override
            public void onFailed(BaseResponse data, String resultMsg, int resultCode) {
                if (callBack != null) {
                    callBack.onFailed(data, resultMsg, resultCode);
                }
                needLogin = true;
                dealWithResultCode(resultCode,null);
            }
        });
    }

    public static void dealWithResultCode(int code,IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        if (code == 2) {
            xiaokOffline();
            getInstance().tryToSignInWifi(callBack);
        }
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
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.addInterceptor(headerInterceptor);
//            if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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

    public WiFiApi getApiService() {
        initRetrofit();
        return retrofit.create(WiFiApi.class);
    }

    /**
     * 便于各个界面调用WiFi登录
     * 内部处理token更新
     */
    public void tryToSignInWifi(IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        TLog.e("OkHttp", "Thread.currentThread().getId() = " + Thread.currentThread().getId());
        initRetrofit();
        // 保证全局单次连接wifi只登录一次
        if (needLogin && !Check.hasContent(apiToken)) {
            toLogin(callBack);
        } else {
            if (callBack != null) {
                callBack.onSuccess(null);
            }
            TLog.w("Current device has logged in !");
        }
    }

    private String getBaseUrl() {
        return baseUrl == null ? Config.Urls.ROUTER_BASE_URL : baseUrl;
    }
}
