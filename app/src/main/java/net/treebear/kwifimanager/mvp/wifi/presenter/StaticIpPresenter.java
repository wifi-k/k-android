package net.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;
import net.treebear.kwifimanager.mvp.wifi.model.StaticIpModel;

/**
 * @author Administrator
 */
public class StaticIpPresenter extends BasePresenter<StaticIpContract.IStaticIpView, StaticIpContract.IStaticIpModel> implements StaticIpContract.IStaticIpPresenter {
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
                mModel.queryNetStatus(new BaseAsyncCallback<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> data) {
                        if (mView != null) {
                            mView.onLoadData(data);
                        }
                    }
                });
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onLoadFail(resultMsg, Config.ServerResponseCode.CUSTOM_ERROR);
                }
            }
        });
    }

    @Override
    public void queryNetStatus() {
        if (mModel == null) {
            return;
        }
        mModel.queryNetStatus(new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onLoadFail("", Config.WifiResponseCode.CONNECT_SUCCESS);
                }
            }
        });
    }
}
