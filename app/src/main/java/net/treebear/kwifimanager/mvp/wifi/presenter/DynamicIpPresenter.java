package net.treebear.kwifimanager.mvp.wifi.presenter;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.http.ApiCode;
import net.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;
import net.treebear.kwifimanager.mvp.wifi.model.DynamicIpModel;

/**
 * @author Administrator
 */
public class DynamicIpPresenter extends BasePresenter<DynamicIpContract.View, DynamicIpContract.Model> implements DynamicIpContract.Presenter {
    @Override
    public void setModel() {
        mModel = new DynamicIpModel();
    }

    @Override
    public void dynamicIpSet() {
        mModel.dynamicIpSet(new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                queryNetStatus();
            }

            @Override
            public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onLoadFail(response, resultMsg, ApiCode.CUSTOM_ERROR);
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
