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

    public interface View extends IView<Object> {

    }

    public interface Presenter extends IPresenter<View> {

        void appLogin(String name, String password);
    }

    public interface Model extends IModel {

        void appLogin(RequestBody params, AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack);

    }
}
