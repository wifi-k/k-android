package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.bean.WifiUserInfo;
import net.treebear.kwifimanager.mvp.wifi.contract.LoginWifiContract;

import okhttp3.RequestBody;

public class LoginWifiModel extends BaseWifiModel implements LoginWifiContract.ILoginModel {

    @Override
    public void appLogin(RequestBody params, AsyncCallBack<BaseResponse<WifiUserInfo>> callBack) {
        bindObservable(mService.appLogin(params), callBack);
    }
}
