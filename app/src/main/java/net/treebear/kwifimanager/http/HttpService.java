package net.treebear.kwifimanager.http;


import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.AdvertisementBean;
import net.treebear.kwifimanager.bean.UserInfoBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author tinlone
 * @date 2017/6/6 0006
 */

public interface HttpService {


    /**
     * 手机APP开屏页
     * from 	string 	是 	（安卓：android；水果：iOS）
     * type 	string 	是
     */
    @POST("user/vcode/getv2")
    Observable<BaseResponse<String>> getSignUpVerify(@Body RequestBody params);

    @POST("user/signup/vcode")
    Observable<BaseResponse<UserInfoBean>> signUpByVerifyCode(@Body RequestBody params);

    @POST("user/passwd/reset")
    Observable<BaseResponse<Object>> setUserPassword(@Body RequestBody params);
}
