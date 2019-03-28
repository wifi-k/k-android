package cn.treebear.kwifimanager.mvp.wifi.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;

/**
 * @author Administrator
 */
public class DynamicIpContract {

    public interface View extends IView<WifiDeviceInfo> {

    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 动态ip上网
         */
        void dynamicIpSet();

        /**
         * 查询网络连通状态
         */
        void queryNetStatus();
    }

    public interface Model extends IModel {
        /**
         * 配置动态ip上网
         */
        void dynamicIpSet(AsyncCallBack<BaseResponse<Object>> callBack);

        /**
         * 查询wifi连接状态
         *
         * @param callBack 回调
         */
        void queryNetStatus(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack);

        /**
         * 获取node
         */
        void getNode(AsyncCallBack<BaseResponse<WifiDeviceInfo>> callBack);

    }
}
