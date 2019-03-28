package cn.treebear.kwifimanager.mvp.wifi.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseWifiModel;
import cn.treebear.kwifimanager.mvp.wifi.contract.ModifyWifiInfoContract;
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
