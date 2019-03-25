package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.FirmwareUpgradeContract;
import net.treebear.kwifimanager.mvp.server.model.FirmwareUpgradeModel;

public class FirmwareUpgradePresenter extends BasePresenter<FirmwareUpgradeContract.View, FirmwareUpgradeContract.Model> implements FirmwareUpgradeContract.Presenter {
    @Override
    public void setModel() {
        mModel = new FirmwareUpgradeModel();
    }

    @Override
    public void upgradeNode(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.upgradeNode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.upgradeNodeVersion(resultData.getCode(), resultData.getMsg());
                }
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.upgradeNodeVersion(resultCode, resultMsg);
                }
            }
        });

    }
}
