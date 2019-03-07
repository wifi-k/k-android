package net.treebear.kwifimanager.mvp.wifi.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class DialUpContract {

    public interface IDialUpView extends IView<Object> {

    }

    public interface IDialUpPresenter extends IPresenter<DialUpContract.IDialUpView> {
        /**
         * 设置宽带账号
         *
         * @param name     宽带账号
         * @param password 账号密码
         */
        void dialUpSet(String name, String password);
        /**
         * 查询网络连通状态
         */
        void queryNetStatus();
    }

    public interface IDialUpModel extends IModel {
        /**
         * 设置宽带账号
         *
         * @param params   账号密码
         * @param callBack 回调
         */
        void dialUpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        /**
         * 查询wifi连接状态
         * @param callBack 回调
         */
        void queryNetStatus(AsyncCallBack<BaseResponse<Object>> callBack);

    }
}
