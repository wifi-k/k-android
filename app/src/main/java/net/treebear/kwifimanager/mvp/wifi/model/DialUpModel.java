package net.treebear.kwifimanager.mvp.wifi.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseWifiModel;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.mvp.wifi.contract.DialUpContract;

import okhttp3.RequestBody;

/**
 * 拨号上网
 *
 * @author Administrator
 */
public class DialUpModel extends BaseWifiModel implements DialUpContract.IDialUpModel {
    @Override
    public void dialUpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.ppoeSet(params), callBack);
    }

    @Override
    public void queryNetStatus(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack) {
        bindObservable(mService.queryWifiStatus(),callBack);
    }
}
