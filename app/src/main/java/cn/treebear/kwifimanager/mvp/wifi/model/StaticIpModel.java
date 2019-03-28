package cn.treebear.kwifimanager.mvp.wifi.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseWifiModel;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;
import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class StaticIpModel extends BaseWifiModel implements StaticIpContract.Model {
    @Override
    public void staticIpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.staticSet(params), callBack);
    }

    @Override
    public void queryNetStatus(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.queryWifiStatus(), callBack);
    }

    @Override
    public void getNode(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.getNode(), callBack);
    }
}