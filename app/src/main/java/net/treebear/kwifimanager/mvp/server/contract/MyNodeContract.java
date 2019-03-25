package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface MyNodeContract {

    interface IMyNodeView extends IView<NodeInfoDetail> {

        void modifyNodeNameResponse(int resultCode, String msg);

        void unbindNodeResponse(int resultCode, String msg);

        void upgardeNodeVersion(int resultCode, String msg);
    }

    interface IMyNodePresenter extends IPresenter<IMyNodeView> {
        void getNodeList();

        void modifyNodeName(String nodeId, String name);

        void unbindNode(String nodeId);

        void upgardeNode(String nodeId);

    }

    interface IMyNodeModel extends IModel {
        void getNodeList(RequestBody params,AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack);

        void modifyNodeName(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void unbindNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void upgardeNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
