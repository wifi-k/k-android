package net.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.wifi.contract.ModifyWifiInfoContract;
import net.treebear.kwifimanager.mvp.wifi.model.ModifyWifiInfoModel;

/**
 * @author Administrator
 */
public class ModifyWifiInfoPresenter extends BasePresenter<ModifyWifiInfoContract.IModifyWifiInfoView,
        ModifyWifiInfoContract.IModifyWifiInfoModel> implements ModifyWifiInfoContract.IModifyWifiInfoPresenter {
    @Override
    public void setModel() {
        mModel = new ModifyWifiInfoModel();
    }

    @Override
    public void modifyWifiInfo(String name, String password) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NAME, name);
        map.put(Keys.PASSWD_WIFI, password);
        mModel.modifyWifiInfo(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onLoadData(resultData);
                }
            }
        });
    }
}
