package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;

import okhttp3.RequestBody;

public class WiFiSettingProxyModel extends BaseWifiModel {
    /**
     * 重启
     */
    public void restart(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.restart(), callBack);
    }

    /**
     * 重置
     */
    public void reset(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.reset(), callBack);
    }

    /**
     * 登录
     */
    public void appLogin(RequestBody params, AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.appLogin(params), callBack);
    }

    /**
     * 获取节点信息
     */
    public void getNodeInfo(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.getNode(), callBack);
    }

    /**
     * 拨号上网
     */
    public void dialUpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.ppoeSet(params), callBack);
    }

    /**
     * 动态IP上网
     */
    public void dynamicIpSet(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.dynamicSet(), callBack);
    }

    /**
     * 查询网络状态
     */
    public void queryNetStatus(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.queryWifiStatus(), callBack);
    }

    /**
     * 修改Wifi信息
     */
    public void modifyWifiInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.ssidSet(params), callBack);
    }

    /**
     * 静态IP上网
     */
    public void staticIpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.staticSet(params), callBack);
    }

}
