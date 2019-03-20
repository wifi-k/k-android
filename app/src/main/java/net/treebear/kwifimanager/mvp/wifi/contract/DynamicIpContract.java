package net.treebear.kwifimanager.mvp.wifi.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

/**
 * @author Administrator
 */
public class DynamicIpContract {

    public interface IDynamicIpView extends IView<WifiDeviceInfo> {

    }

    public interface IDynamicIpPresenter extends IPresenter<IDynamicIpView> {
        /**
         * 动态ip上网
         */
        void dynamicIpSet();

        /**
         * 查询网络连通状态
         */
        void queryNetStatus();
    }

    public interface IDynamicIpModel extends IModel {
        /**
         * 配置动态ip上网
         */
        void dynamicIpSet(AsyncCallBack<BaseResponse<Object>> callBack);

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
