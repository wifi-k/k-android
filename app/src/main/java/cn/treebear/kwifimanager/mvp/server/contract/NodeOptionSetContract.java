package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.NodeWifiListBean;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface NodeOptionSetContract {

    interface View extends IView<NodeWifiListBean> {
        void onSSIDResponseOK();

        void onPwdResponseOK();

    }

    interface Presenter extends IPresenter<View> {
        void getNodeSsid(String nodeId);

        void modifySsid(String nodeId, int freq, String ssid);

        void modifyPasswd(String nodeId, int freq, String passwd);
    }

    interface Model extends IModel {
        void getNodeSsid(RequestBody params, AsyncCallBack<BaseResponse<NodeWifiListBean>> callBack);

        void modifySsid(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void modifyPasswd(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

    }
}
