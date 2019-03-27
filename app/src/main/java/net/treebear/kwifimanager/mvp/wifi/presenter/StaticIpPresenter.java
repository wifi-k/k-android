package net.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.http.ApiCode;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;
import net.treebear.kwifimanager.mvp.wifi.model.StaticIpModel;

/**
 * @author Administrator
 */
public class StaticIpPresenter extends BasePresenter<StaticIpContract.View, StaticIpContract.Model> implements StaticIpContract.Presenter {
    @Override
    public void setModel() {
        mModel = new StaticIpModel();
    }

    @Override
    public void staticIpSet(String ip, String netmask, String gateway, String dns1, String dns2) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.IP, ip);
        map.put(Keys.NET_MASK, netmask);
        map.put(Keys.GATEWAY, gateway);
        map.put(Keys.DNS1, dns1);
        map.put(Keys.DNS2, dns2);
        mModel.staticIpSet(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                queryNetStatus();
            }

            @Override
            public void onFailed(BaseResponse response,String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onLoadFail(response,resultMsg, ApiCode.CUSTOM_ERROR);
                }
            }
        });
    }

    @Override
    public void queryNetStatus() {
        if (mModel == null) {
            return;
        }
        mModel.queryNetStatus(new BaseAsyncCallback<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
//                if (mView != null) {
//                    mView.onLoadFail("", Config.WifiResponseCode.CONNECT_SUCCESS);
//                }
                getNode();
            }
        });
    }

    public void getNode() {
        if (mModel == null) {
            return;
        }
        mModel.getNode(new BaseAsyncCallback<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                if (mView != null) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}
