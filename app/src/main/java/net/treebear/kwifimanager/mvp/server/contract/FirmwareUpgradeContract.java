package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

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
