package net.treebear.kwifimanager.http;


import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.ServerUserInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author tinlone
 * @date 2017/6/6 0006
 */

public interface HttpService {
    /**
     * 获取验证码
     *
     * @param params 请求体数据包装类
     *               * type*	int	1-注册，2-忘记密码,3-修改手机号 4-登录
     *               * mobile*	str
     */
    @POST("user/vcode/getv2")
    Observable<BaseResponse<String>> getVerifyByType(@Body RequestBody params);

    /**
     * 通过验证码注册
     *
     * @param params 请求体数据包装类
     *               * mobile*	str	手机号
     *               * vcode*	str	手机验证码
     */
    @POST("user/signup/vcode")
    Observable<BaseResponse<ServerUserInfo>> signUpByVerifyCode(@Body RequestBody params);

    /**
     * 设置/更改密码
     *
     * @param params 请求体数据包装类
     *               * passwd*	str	新密码
     */
    @POST("user/passwd/reset")
    Observable<BaseResponse<Object>> setUserPassword(@Body RequestBody params);

    /**
     * 验证码登录
     *
     * @param params 请求体数据包装类
     *               * mobile*	str	手机号
     *               * vcode*	str	手机验证码
     */
    @POST("user/signin/vcode")
    Observable<BaseResponse<ServerUserInfo>> signInByCode(@Body RequestBody params);

    /**
     * 密码登录
     *
     * @param params 请求体数据包装类
     *               * mobile*	str	手机号
     *               * passwd*	str	md5后的值
     *               * imgCodeId*	str	图像验证码临时Id
     *               * imgCode*	str	图像验证码，图片和数字
     */
    @POST("user/signin/passwd")
    Observable<BaseResponse<ServerUserInfo>> signinByPassword(@Body RequestBody params);

    /**
     * 忘记密码
     *
     * @param params 请求体数据包装类
     *               * mobile*	str
     *               * vcode*	str	手机验证码
     *               * passwd*	str	新密码
     */
    @POST("user/passwd/forget")
    Observable<BaseResponse<Object>> forgetPassword(@Body RequestBody params);

    /**
     * 获取用户信息
     * 参数 header中的token
     */
    @POST("user/info/get")
    Observable<BaseResponse<ServerUserInfo>> getUserInfo();

    /**
     * 设置用户信息
     *
     * @param params 请求数据包装
     *               *   name	str
     *               * avatar	str	头像地址，先不做
     */
    @POST("user/info/set")
    Observable<BaseResponse<Object>> setUserInfo(@Body RequestBody params);

    /**
     * 更新用户手机号
     *
     * @param params 请求数据包装
     *               * mobile*	str
     *               * vcode*	str
     */
    @POST("user/mobile/verify")
    Observable<BaseResponse<Object>> modifyUserMobile(@Body RequestBody params);

    /**
     * 绑定节点
     *
     * @param params 请求数据包装
     *               * nodeId   str
     */
    @POST("user/node/bind")
    Observable<BaseResponse<Object>> bindNode(@Body RequestBody params);

}
