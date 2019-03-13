package net.treebear.kwifimanager.http;


import android.util.ArrayMap;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.wifi.model.LoginWifiModel;
import net.treebear.kwifimanager.util.RequestBodyUtils;
import net.treebear.kwifimanager.util.SecurityUtils;
import net.treebear.kwifimanager.util.TLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static LoginWifiModel loginWifiModel;
    private String baseUrl = Config.Urls.ROUTER_BASE_URL;
    private Retrofit retrofit;
    private static String apiToken = "";
    private static boolean needLogin = true;

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
//                String cookies[] = ((String) SharedPreferencesUtil.getParam(MyApplication.getAppContext(), "cookies", "")).split("-");
//                for (String cookie : cookies) {
//                    builder.addHeader("Cookie", cookie);
//                }
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
            builder.connectTimeout(160, TimeUnit.SECONDS);
            builder.readTimeout(160, TimeUnit.SECONDS);
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

    public static void try1() {
        Interceptor headerInterceptor = chain -> {
            Request.Builder builder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Api-Token", apiToken)
                    .addHeader("Accept-Encoding", "utf-8")
                    .addHeader("Api-Version", "2019.3.1");
            Request request = builder.build();
            return chain.proceed(request);
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(headerInterceptor)
                .connectTimeout(160, TimeUnit.SECONDS)
                .readTimeout(160, TimeUnit.SECONDS)
                .build();
        FormBody body = new FormBody.Builder().add(Keys.NAME, "admin")
                .add(Keys.PASSWD_WIFI, SecurityUtils.md5(Config.Text.XIAO_K_WIFI_PASSOWRD))
                .build();
        Request req = new Request.Builder().url(Config.Urls.ROUTER_BASE_URL + "app/login")
                .post(body)
                .build();
        okHttpClient.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                TLog.e(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                TLog.i(body);
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    int code = jsonObject.getInt("code");
                    if (code == 0){
                        apiToken = jsonObject.getJSONObject("data").getString("token");
                        updataApiToken(apiToken);
                        getDeviceSerialId();
                        getDeviceOnlineStatus();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 便于各个界面调用WiFi登录
     * 内部处理token更新
     */
    public static void tryToSignInWifi(IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        // 保证全局单次连接wifi只登录一次
        if (!needLogin) {
            TLog.keep("Current device has logged in !");
            return;
        }
        if (loginWifiModel == null) {
            loginWifiModel = new LoginWifiModel();
        }
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put(Keys.NAME, "admin");
        map.put(Keys.PASSWD_WIFI, SecurityUtils.md5(Config.Text.XIAO_K_WIFI_PASSOWRD));
        loginWifiModel.appLogin(RequestBodyUtils.convert(map), new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                TLog.e("OkHttp", "WiFi login success !" + TLog.valueOf(resultData));
                ToastUtils.showShort(TLog.valueOf(resultData.getData().toString()));
                if (resultData.getData() != null) {
                    apiToken = resultData.getData().getToken();
                    updataApiToken(apiToken);
                    MyApplication.getAppContext().getDeviceInfo().setToken(apiToken);
                    needLogin = false;
                    if (callBack != null) {
                        callBack.onSuccess(resultData);
                    }
                   getDeviceSerialId();
                    getDeviceOnlineStatus();
                }
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

    private static void getDeviceSerialId() {
        loginWifiModel.getNodeInfo(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                TLog.i(resultData);
                if (resultData != null && resultData.getData() != null) {
                    MyApplication.getAppContext().getDeviceInfo().setId(resultData.getData().getId());
                }
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                TLog.e("OkHttp", "WiFi login failed , code : " + resultCode + ", message : " + resultMsg);
            }
        });
    }

    private static void getDeviceOnlineStatus() {
        loginWifiModel.queryNetStatus(new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                TLog.i("getDeviceOnlineStatus ： "+TLog.valueOf(resultData));
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
