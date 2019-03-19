package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;

import okhttp3.RequestBody;

public class WiFiSettingProxyModel extends BaseWifiModel {

    public void restart(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.restart(), callBack);
    }

    public void reset(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.reset(), callBack);
    }

    public void appLogin(RequestBody params, AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.appLogin(params), callBack);
    }

    public void getNodeInfo(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.getNode(), callBack);
    }

    public void queryNetStatus(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.queryWifiStatus(), callBack);
    }
}
