package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.mvp.wifi.contract.LoginWifiContract;

import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class LoginWifiModel extends BaseWifiModel implements LoginWifiContract.ILoginModel {

    @Override
    public void appLogin(RequestBody params, AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.appLogin(params), callBack);
    }

    public void getNodeInfo(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.getNode(), callBack);
    }

    public void queryNetStatus(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.queryWifiStatus(),callBack);
    }
}
