package cn.treebear.kwifimanager.http;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public interface WiFiApi {
    /**
     * App登录
     * * name	str	初始用户名, admin
     * * passwd	str	初始密码,app端md5(123456)-e10adc3949ba59abbe56e057f20f883e,wifi端md5(app+xiaok)
     */
    @POST("app/login")
    Observable<BaseResponse<WifiDeviceInfo>> appLogin(@Body RequestBody params);

    /**
     * 设置路由器使用拨号上网
     * ame	str	宽带账号
     * passwd	str	账号密码
     */
    @POST("pppoe/set")
    Observable<BaseResponse<Object>> ppoeSet(@Body RequestBody params);

    /**
     * 设置静态IP参数上网
     * ip	str	xxx.xxx.xxx.xxx
     * netmask	str	xxx.xxx.xxx.xxx
     * gateway	str	xxx.xxx.xxx.xxx
     * dns1	str	xxx.xxx.xxx.xxx
     * dns2	str
     */
    @POST("static/set")
    Observable<BaseResponse<Object>> staticSet(@Body RequestBody params);

    /**
     * 使用动态IP
     */
    @POST("dynamic/set")
    Observable<BaseResponse<Object>> dynamicSet();

    /**
     * 查询网络状态
     */
    @POST("network/status")
    Observable<BaseResponse<WifiDeviceInfo>> queryWifiStatus();

    /**
     * 设置wifi参数
     */
    @POST("ssid/set")
    Observable<BaseResponse<Object>> ssidSet(@Body RequestBody params);

    /**
     * 获取节点序列号
     */
    @POST("node/get")
    Observable<BaseResponse<WifiDeviceInfo>> getNode();

    /**
     * 重启
     */
    @POST("node/restart")
    Observable<BaseResponse<Object>> restart();

    /**
     * 重置
     */
    @POST("node/reset")
    Observable<BaseResponse<Object>> reset();
}