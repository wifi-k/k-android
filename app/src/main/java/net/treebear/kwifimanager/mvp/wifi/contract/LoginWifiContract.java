package net.treebear.kwifimanager.mvp.wifi.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class LoginWifiContract {

    public interface ILoginView extends IView<WifiDeviceInfo> {

    }

    public interface ILoginPresenter extends IPresenter<LoginWifiContract.ILoginView> {

        void appLogin(String name, String password);
    }

    public interface ILoginModel extends IModel {

        void appLogin(RequestBody params, AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack);

    }
}
