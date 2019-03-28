package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface FirmwareUpgradeContract {

    interface View extends IView<Object> {

        void upgradeNodeVersion(int resultCode, String msg);
    }

    interface Presenter extends IPresenter<View> {

        void upgradeNode(String nodeId);
    }

    interface Model extends IModel {

        void upgradeNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
