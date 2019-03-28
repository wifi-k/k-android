package cn.treebear.kwifimanager.mvp.wifi.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
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
