package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.mvp.server.contract.BindHomeContract;

import okhttp3.RequestBody;

public class BindHomeModel extends BaseServerModel implements BindHomeContract.Model {
    @Override
    public void getNodeList(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack) {
        bindObservable(mService.getNodeList(params), callBack);
    }
}
