package cn.treebear.kwifimanager.mvp.wifi.presenter;

import android.os.Build;
import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.wifi.contract.ModifyWifiInfoContract;
import cn.treebear.kwifimanager.mvp.wifi.model.ModifyWifiInfoModel;
import cn.treebear.kwifimanager.util.SecurityUtils;

/**
 * @author Administrator
 */
public class ModifyWifiInfoPresenter extends BasePresenter<ModifyWifiInfoContract.View,
        ModifyWifiInfoContract.Model> implements ModifyWifiInfoContract.Presenter {
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
