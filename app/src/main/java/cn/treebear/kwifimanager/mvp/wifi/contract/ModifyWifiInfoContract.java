package cn.treebear.kwifimanager.mvp.wifi.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class ModifyWifiInfoContract {

    public interface View extends IView<Object> {

    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 设置宽带账号
         *
         * @param name     宽带账号
         * @param password 账号密码
         */
        void modifyWifiInfo(String bssid, String name, String password);
    }

    public interface Model extends IModel {
        /**
         * 设置宽带账号
         *
         * @param params   账号密码
         * @param callBack 回调
         */
        void modifyWifiInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

    }
}
