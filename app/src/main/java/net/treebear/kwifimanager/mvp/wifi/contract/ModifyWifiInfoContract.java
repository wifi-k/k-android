package net.treebear.kwifimanager.mvp.wifi.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class ModifyWifiInfoContract {

    public interface IModifyWifiInfoView extends IView<Object> {

    }

    public interface IModifyWifiInfoPresenter extends IPresenter<ModifyWifiInfoContract.IModifyWifiInfoView> {
        /**
         * 设置宽带账号
         *
         * @param name     宽带账号
         * @param password 账号密码
         */
        void modifyWifiInfo(String name, String password);
    }

    public interface IModifyWifiInfoModel extends IModel {
        /**
         * 设置宽带账号
         *
         * @param params   账号密码
         * @param callBack 回调
         */
        void modifyWifiInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

    }
}
