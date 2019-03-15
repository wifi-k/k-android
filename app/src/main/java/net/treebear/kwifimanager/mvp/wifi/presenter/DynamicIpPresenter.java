package net.treebear.kwifimanager.mvp.wifi.presenter;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;
import net.treebear.kwifimanager.mvp.wifi.model.DynamicIpModel;

/**
 * @author Administrator
 */
public class DynamicIpPresenter extends BasePresenter<DynamicIpContract.IDynamicIpView, DynamicIpContract.IDynamicIpModel> implements DynamicIpContract.IDynamicIpPresenter {
    @Override
    public void setModel() {
        mModel = new DynamicIpModel();
    }

    @Override
    public void dynamicIpSet() {
        mModel.dynamicIpSet(new BaseAsyncCallback<BaseResponse<Object>>() {
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
