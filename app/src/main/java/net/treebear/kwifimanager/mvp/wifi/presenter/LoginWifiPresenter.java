package net.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.http.WiFiHttpClient;
import net.treebear.kwifimanager.mvp.wifi.contract.LoginWifiContract;
import net.treebear.kwifimanager.mvp.wifi.model.LoginWifiModel;

public class LoginWifiPresenter extends BasePresenter<LoginWifiContract.View, LoginWifiContract.Model> implements LoginWifiContract.Presenter {
    @Override
    public void setModel() {
        mModel = new LoginWifiModel();
    }

    @Override
    public void appLogin(String name, String password) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NAME, name);
        map.put(Keys.PASSWD_WIFI, password);
        mModel.appLogin(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<WifiDeviceInfo>>() {
            @Override
            public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                WifiDeviceInfo data = resultData.getData();
                if (data != null) {
                    if (mView != null) {
                        mView.onLoadData(data);
                    }
                    WiFiHttpClient.updataApiToken(data.getToken());
                }
            }
        });
    }
}
