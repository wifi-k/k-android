package cn.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.mvp.wifi.contract.LoginWifiContract;
import cn.treebear.kwifimanager.mvp.wifi.model.LoginWifiModel;

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
