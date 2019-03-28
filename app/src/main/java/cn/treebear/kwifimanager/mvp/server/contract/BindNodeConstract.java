package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
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
