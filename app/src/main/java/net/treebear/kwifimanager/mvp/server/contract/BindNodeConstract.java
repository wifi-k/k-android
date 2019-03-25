package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface BindNodeConstract {

    interface View extends IView<Object> {

    }

    interface Presenter extends IPresenter<View> {
        void bindNode(String nodeId);
    }

    interface Model extends IModel {

        void bindNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }
}
