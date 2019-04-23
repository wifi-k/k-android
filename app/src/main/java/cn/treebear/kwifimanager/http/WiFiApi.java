package cn.treebear.kwifimanager.http;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

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
     * .addHeader("Api-Token", apiToken)
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

    /**
     * 文件上传
     *
     * @param userId 用户Id
     * @param file   文件分片
     * @return 成功回调
     */
    @Multipart
    @POST("node/file/upload")
    Observable<BaseResponse<Object>> nodeFileUpload(@Header("Api-User") long userId, @Part MultipartBody.Part file);

    /**
     * 下载文件
     *
     * @return 流
     */
    @Streaming
    @POST("node/file/get")
    Observable<ResponseBody> getNodeFile(@Header("Api-User") long userId, @Query("uri") String path);

    /**
     *
     * @param userId
     * @param path
     * @return
     */
    @POST("node/file/del")
    Observable<BaseResponse<Object>> nodeFileDelete(@Header("Api-User") long userId, @Query("uri") String path);
}
