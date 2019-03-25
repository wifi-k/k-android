package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.mvp.server.contract.MyNodeContract;

import okhttp3.RequestBody;

public class MyNodeModel extends BaseServerModel implements MyNodeContract.IMyNodeModel {
    @Override
    public void getNodeList(RequestBody params,AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack) {
        bindObservable(mService.getNodeList(params), callBack);
    }

    @Override
    public void modifyNodeName(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeInfo(params), callBack);
    }

    @Override
    public void unbindNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.unbindNode(params), callBack);
    }

    @Override
    public void upgardeNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {

    }
}
