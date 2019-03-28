package cn.treebear.kwifimanager.mvp.wifi.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseWifiModel;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;

/**
 * @author Administrator
 */
public class DynamicIpModel extends BaseWifiModel implements DynamicIpContract.Model {
    @Override
    public void dynamicIpSet(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.dynamicSet(), callBack);
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
