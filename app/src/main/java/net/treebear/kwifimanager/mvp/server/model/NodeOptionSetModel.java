package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.mvp.server.contract.NodeOptionSetContract;

import okhttp3.RequestBody;

public class NodeOptionSetModel extends BaseServerModel implements NodeOptionSetContract.Model {
    @Override
    public void modifySsid(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeSsid(params), callBack);
    }

    @Override
    public void modifyPasswd(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeSsid(params), callBack);
    }
}
