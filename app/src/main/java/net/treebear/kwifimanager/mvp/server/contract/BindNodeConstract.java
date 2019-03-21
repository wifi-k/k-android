package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface BindNodeConstract {

    interface IBindNodeView extends IView<Object> {

    }

    interface IBindNodePresenter extends IPresenter<IBindNodeView> {
        void bindNode(String nodeId);
    }

    interface IBindNodeModel extends IModel {

        void bindNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }
}
