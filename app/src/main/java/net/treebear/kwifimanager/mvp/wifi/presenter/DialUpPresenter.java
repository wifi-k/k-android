package net.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.wifi.contract.DialUpContract;
import net.treebear.kwifimanager.mvp.wifi.model.DialUpModel;

/**
 * @author Administrator
 */
public class DialUpPresenter extends BasePresenter<DialUpContract.IDialUpView, DialUpContract.IDialUpModel> implements DialUpContract.IDialUpPresenter {
    @Override
    public void setModel() {
        mModel = new DialUpModel();
    }

    @Override
    public void dialUpSet(String name, String password) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NAME, name);
        map.put(Keys.PASSWD_WIFI, password);
        mModel.dialUpSet(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
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
        });
    }

    @Override
    public void queryNetStatus() {
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
