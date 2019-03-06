package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
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
    public void queryNetStatus(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.queryWifiStatus(),callBack);
    }
}
