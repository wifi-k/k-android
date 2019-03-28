package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.mvp.server.contract.MyNodeContract;
import okhttp3.RequestBody;

public class MyNodeModel extends BaseServerModel implements MyNodeContract.Model {
    @Override
    public void getNodeList(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack) {
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
    public void upgradeNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.firmwareUpgrade(params), callBack);
    }
}
