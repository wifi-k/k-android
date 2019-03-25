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
public class StaticIpContract {

    public interface View extends IView<WifiDeviceInfo> {

    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 设置宽带账号
         * ip	str	xxx.xxx.xxx.xxx
         * netmask	str	xxx.xxx.xxx.xxx
         * gateway	str	xxx.xxx.xxx.xxx
         * dns1	str	xxx.xxx.xxx.xxx
         * dns2	str
         */
        void staticIpSet(String ip, String netmask, String gateway, String dns1, String dns2);

        /**
         * 查询网络状态
         */
        void queryNetStatus();
    }

    public interface Model extends IModel {
        /**
         * 设置宽带账号
         *
         * @param params   账号密码
         * @param callBack 回调
         */
        void staticIpSet(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        /**
         * 查询wifi连接状态
         * @param callBack 回调
         */
        void queryNetStatus(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack);

        /**
         * 获取node
         */
        void getNode(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack);

    }
}
