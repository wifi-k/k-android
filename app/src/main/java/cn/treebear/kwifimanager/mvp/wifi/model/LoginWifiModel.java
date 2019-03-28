package cn.treebear.kwifimanager.mvp.wifi.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseWifiModel;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.mvp.wifi.contract.LoginWifiContract;
import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class LoginWifiModel extends BaseWifiModel implements LoginWifiContract.Model {

    @Override
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
