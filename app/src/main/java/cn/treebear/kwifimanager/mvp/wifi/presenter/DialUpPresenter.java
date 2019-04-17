package cn.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.mvp.wifi.contract.DialUpContract;
import cn.treebear.kwifimanager.mvp.wifi.model.DialUpModel;
import cn.treebear.kwifimanager.util.TLog;

/**
 * @author Administrator
 */
public class DialUpPresenter extends BasePresenter<DialUpContract.View, DialUpContract.Model> implements DialUpContract.Presenter {
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
                if (mView != null) {
                    getNode();
                }
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
                TLog.w("okokokokokokok");
                if (mView != null) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}
