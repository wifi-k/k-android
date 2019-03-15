package net.treebear.kwifimanager.mvp.wifi.presenter;

import android.os.Build;
import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.wifi.contract.ModifyWifiInfoContract;
import net.treebear.kwifimanager.mvp.wifi.model.ModifyWifiInfoModel;
import net.treebear.kwifimanager.util.SecurityUtils;

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
    public void modifyWifiInfo(String ssid0, String ssid, String password) {
        ArrayMap<String, Object> map = map();
        String so0 = ssid0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (ssid0.startsWith("\"") && ssid0.endsWith("\"")) {
                so0 = ssid0.substring(1, ssid0.length() - 1);
            }
        }
        map.put(Keys.SSID0, so0);
        map.put(Keys.SSID, ssid);
        map.put(Keys.PASSWD_WIFI, SecurityUtils.md5(password));
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
