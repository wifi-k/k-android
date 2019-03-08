package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;

import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class StaticIpModel extends BaseWifiModel implements StaticIpContract.IStaticIpModel {
    @Override
    public void staticIpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.staticSet(params), callBack);
    }

    @Override
    public void queryNetStatus(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.queryWifiStatus(),callBack);
    }
}