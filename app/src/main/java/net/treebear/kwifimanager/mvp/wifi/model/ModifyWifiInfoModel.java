package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.mvp.wifi.contract.ModifyWifiInfoContract;

import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class ModifyWifiInfoModel extends BaseWifiModel implements ModifyWifiInfoContract.Model {
    @Override
    public void modifyWifiInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.ssidSet(params), callBack);
    }
}
