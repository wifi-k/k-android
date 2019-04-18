package cn.treebear.kwifimanager.http;


import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.bean.FamilyMemberCover;
import cn.treebear.kwifimanager.bean.HealthyModelBean;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.bean.NodeWifiListBean;
import cn.treebear.kwifimanager.bean.QiNiuUserBean;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.bean.TimeControlbean;
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
     * @param body 请求体数据包装类
     *             * type*	int	1-注册，2-忘记密码,3-修改手机号 4-登录
     *             * mobile*	str
     */
    @POST("user/vcode/getv2")
    Observable<BaseResponse<String>> getVerifyByType(@Body RequestBody body);

    /**
     * 通过验证码注册
     *
     * @param body 请求体数据包装类
     *             * mobile*	str	手机号
     *             * vcode*	str	手机验证码
     */
    @POST("user/signup/vcode")
    Observable<BaseResponse<ServerUserInfo>> signUpByVerifyCode(@Body RequestBody body);

    /**
     * 设置/更改密码
     *
     * @param body 请求体数据包装类
     *             * passwd*	str	新密码
     */
    @POST("user/passwd/reset")
    Observable<BaseResponse<Object>> setUserPassword(@Body RequestBody body);

    /**
     * 验证码登录
     *
     * @param body 请求体数据包装类
     *             * mobile*	str	手机号
     *             * vcode*	str	手机验证码
     */
    @POST("user/signin/vcode")
    Observable<BaseResponse<ServerUserInfo>> signInByCode(@Body RequestBody body);

    /**
     * 密码登录
     *
     * @param body 请求体数据包装类
     *             * mobile*	str	手机号
     *             * passwd*	str	md5后的值
     *             * imgCodeId*	str	图像验证码临时Id
     *             * imgCode*	str	图像验证码，图片和数字
     */
    @POST("user/signin/passwd")
    Observable<BaseResponse<ServerUserInfo>> signinByPassword(@Body RequestBody body);

    /**
     * 忘记密码
     *
     * @param body 请求体数据包装类
     *             * mobile*	str
     *             * vcode*	str	手机验证码
     *             * passwd*	str	新密码
     */
    @POST("user/passwd/forget")
    Observable<BaseResponse<Object>> forgetPassword(@Body RequestBody body);

    /**
     * 获取用户信息
     * 参数 header中的token
     */
    @Deprecated
    @POST("user/info/get")
    Observable<BaseResponse<ServerUserInfo>> getUserInfo();

    /**
     * 获取用户信息
     * 参数 header中的token
     */
//    @POST("user/info/get")
    @POST("user/info/getext")
    Observable<BaseResponse<SUserCover>> getUserInfoExt(@Body RequestBody body);

    /**
     * 设置用户信息
     *
     * @param body 请求数据包装
     *             *   name	str
     *             * avatar	str	头像地址，先不做
     */
    @POST("user/info/set")
    Observable<BaseResponse<Object>> setUserInfo(@Body RequestBody body);

    /**
     * 更新用户手机号
     *
     * @param body 请求数据包装
     *             * mobile*	str
     *             * vcode*	str
     */
    @POST("user/mobile/verify")
    Observable<BaseResponse<Object>> modifyUserMobile(@Body RequestBody body);

    /**
     * 绑定节点
     *
     * @param body 请求数据包装
     *             * nodeId   str
     */
    @POST("user/node/bind")
    Observable<BaseResponse<Object>> bindNode(@Body RequestBody body);

    /**
     * 获取七牛云的token'
     */
    @POST("user/qiniu/get")
    Observable<BaseResponse<QiNiuUserBean>> getQiNiuToken();

    /**
     * 查询用户消息
     *
     * @param body 分页
     *             * pageNo	int	分页号,默认1
     *             * pageSize	int	一页的数据,默认10
     */
    @POST("user/message/list")
    Observable<BaseResponse<MessageInfoBean>> getMessageList(@Body RequestBody body);

    /**
     * 获取节点的ssid user/node/wifi/list
     *
     * @param body * nodeId*	str
     */
    @POST("user/node/wifi/list")
    Observable<BaseResponse<NodeWifiListBean>> getNodeSSIDList(@Body RequestBody body);

    /**
     * 设置节点ssid /user/node/ssid/set
     * <p>
     * 说明
     * freq=0时,表示更新节点下所有的ssid或passwd
     *
     * @param body * nodeId*	str
     *             * freq*	int	1-2.4G,2-5G
     *             * ssid	str	新的ssid名称
     *             * passwd	str	新的md5密码
     *             * rssi	int	信号强度, [0,-100]dbm,好>=-50,强>=-70,中<-70,差<-80
     */
    @POST("user/node/wifi/set")
    Observable<BaseResponse<Object>> setNodeSsid(@Body RequestBody body);

    /**
     * 节点解绑 /user/node/unbind
     *
     * @param body * nodeId*	str
     */
    @POST("user/node/unbind")
    Observable<BaseResponse<Object>> unbindNode(@Body RequestBody body);

    /**
     * 节点信息修改 /user/node/info/set
     *
     * @param body * nodeId*	str
     *             * name	str	节点名称
     */
    @POST("user/node/info/set")
    Observable<BaseResponse<Object>> setNodeInfo(@Body RequestBody body);

    /**
     * 节点加入共享计划 /user/node/gallery_share_white/join
     *
     * @param body * nodeId*	str
     */
    @POST("user/node/gallery_share_white/join")
    Observable<BaseResponse<Object>> joinShare(@Body RequestBody body);

    /**
     * 离开共享计划 /user/node/gallery_share_white/quit
     * 说明
     * 节点完成当前任务后离开
     * 提前预约3天离开
     * 产生一个系统通知给我们
     *
     * @param body * nodeId*	str
     */
    @POST("user/node/gallery_share_white/quit")
    Observable<BaseResponse<Object>> quiteShare(@Body RequestBody body);

    /**
     * 节点信息查询 /user/node/list
     * <p>
     * 说明
     * 分页显示节点基本信息,按bind时间降序排列
     * 显示总数(>=0)、异常(2,3)、离线(0) TODO
     * req
     * <p>
     * 字段	类型	说明
     * nodeId	str	nodeId不传或为空字符串时,查所有
     * status	int[]	0-offline 1-normal 2-warn 3-error,status不传或为空时忽略这个条件
     * pageNo	int	分页号,默认1
     * pageSize	int	一页的数据,默认10,最多10
     */
//    @POST("user/node/list")
//    Observable<BaseResponse<NodeInfoDetail>> getNodeList(@Body RequestBody body);
    @POST("user/node/listall")
    Observable<BaseResponse<NodeInfoDetail>> getNodeList(@Body RequestBody body);

    /**
     * 用户选定节点 /user/node/select
     * 这种交互实现不好
     * 字段	类型	说明
     * nodeId*	str	选择的节点ID
     */
    @POST("user/node/select")
    Observable<BaseResponse<Object>> selectNode(@Body RequestBody body);

    /**
     * 升级节点固件 /user/node/firmware/upgrade
     *
     * @param body * nodeId*	str
     */
    @POST("user/node/firmware/upgrade")
    Observable<BaseResponse<Object>> firmwareUpgrade(@Body RequestBody body);

    /**
     * 用户退出 /user/quit
     */
    @POST("user/quit")
    Observable<BaseResponse<Object>> userQuit();

    /**
     * 加入家庭 /user/node/family/join
     * 字段	类型	说明
     * inviteCode*	str	节点的邀请码
     */
    @POST("user/node/family/join")
    Observable<BaseResponse<Object>> joinFamilyByCode(@Body RequestBody body);

    /**
     * 修改家庭成员信息 /user/node/family/set
     * 字段	类型	说明
     * nodeId*	str
     * userName	str	修改昵称
     */
    @POST("user/node/family/set")
    Observable<BaseResponse<Object>> setFamilyInfo(@Body RequestBody body);

    /**
     * 删除家庭成员 /user/node/family/quit
     * 字段	类型	说明
     * nodeId*	str
     * userId*	long	删除的成员ID
     */
    @POST("user/node/family/quit")
    Observable<BaseResponse<Object>> quitFamily(@Body RequestBody body);

    /**
     * 家庭成员列表 /user/node/family/list
     * 这里不分页
     * 字段	类型	说明
     * nodeId*	str
     */
    @POST("user/node/family/list")
    Observable<BaseResponse<FamilyMemberCover>> getFamilyMembers(@Body RequestBody body);

    /**
     * 获取信号定时配置 /user/node/wifi/timer/get
     * 说明
     * 健康模式, rssi默认值=5
     * wifi格式 [{“freq”:0-所有,”rssi”:5,”timer”:[{“startTime”:”23:00”,”endTime”:”06:00”},{}]},{}]
     */
    @POST("user/node/wifi/timer/get")
    Observable<BaseResponse<HealthyModelBean>> getHealthyModelInfo(@Body RequestBody body);

    /**
     * 设置信号定时配置 /user/node/wifi/timer/set
     * 字段	类型	说明
     * nodeId*	str
     * op*	int	“op”:1-开启 or 0-关闭
     * wifi	str	不传时表示无任何配置，此时op=0
     * * * * * wifi格式 [{“freq”:0-所有,”rssi”:5,”timer”:[{“startTime”:”23:00”,”endTime”:”06:00”},{}]},{}]
     */
    @POST("user/node/wifi/timer/set")
    Observable<BaseResponse<Object>> setHealthyModelInfo(@Body RequestBody body);

    /**
     * 用户微信扫码注册登录 /user/signin/wechat //M1不做
     * 说明 微信扫码注册登录
     */
    @POST("user/signin/wechat")
    Observable<BaseResponse<ServerUserInfo>> wechatSignIn();

    /**
     * 获取上网设备 /user/node/device/list
     * 字段	类型	说明
     * nodeId*	str
     * pageNo	int	分页号,默认1
     * pageSize	int	一页的数据,默认10
     */
    @POST("user/node/device/list")
    Observable<BaseResponse<MobileListBean>> getNodeDeviceList(@Body RequestBody body);

    /**
     * 修改设备信息 /user/node/device/set
     * 字段	类型	说明
     * nodeId*	str
     * mac*	str
     * note	str	备注名
     * block	int	0-allow or 1-block
     */
    @POST("user/node/device/set")
    Observable<BaseResponse<Object>> setNodeDevice(@Body RequestBody body);

    /**
     * 设置设备允许上网配置 /user/node/device/allow/set
     * 说明
     * 限制最多100个
     * mac最多10个
     * wt定义 1-Mon Tue-2 Wen-4 Thu-8 Fri-16 Sat-32 Sun-64
     */
    @POST("user/node/device/allow/set")
    Observable<BaseResponse<Object>> setAllowDevice(@Body RequestBody body);

    /**
     * 获取设备上网配置 /user/node/device/allow/list
     * 说明
     * 这里不分页,最多返回100个
     * 字段	类型	说明
     * nodeId*
     */
    @POST("user/node/device/allow/list")
    Observable<BaseResponse<TimeControlbean>> getAllowDevice(@Body RequestBody body);

    /**
     * 删除设备上网配置 /user/node/device/allow/del
     * 字段	类型	说明
     * nodeId*	str
     * id*	long	配置id
     */
    @POST("user/node/device/allow/del")
    Observable<BaseResponse<Object>> delAllowDevice(@Body RequestBody body);

    /**
     * 获取设备一周上网时长 /user/node/device/week/list
     * 说明
     * 返回开启上网关爱的设备统计
     * App显示的文案用remark字段
     */
    @POST("user/node/device/week/list")
    Observable<BaseResponse<ChildrenListBean>> getChildrenList(@Body RequestBody body);

}
