package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;

/**
 * @author Administrator
 */
public class DynamicIpModel extends BaseWifiModel implements DynamicIpContract.IDynamicIpModel {
    @Override
    public void dynamicIpSet(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.dynamicSet(), callBack);
    }

    @Override
    public void queryNetStatus(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.queryWifiStatus(),callBack);
    }

    @Override
    public void getNode(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.getNode(),callBack);
    }
}
