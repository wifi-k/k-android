package cn.treebear.kwifimanager.mvp.wifi.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseWifiModel;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.mvp.wifi.contract.DialUpContract;
import okhttp3.RequestBody;

/**
 * 拨号上网
 *
 * @author Administrator
 */
public class DialUpModel extends BaseWifiModel implements DialUpContract.Model {
    @Override
    public void dialUpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.ppoeSet(params), callBack);
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
