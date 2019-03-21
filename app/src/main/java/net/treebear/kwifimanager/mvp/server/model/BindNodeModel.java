package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.mvp.server.contract.BindNodeConstract;

import okhttp3.RequestBody;

public class BindNodeModel extends BaseServerModel implements BindNodeConstract.IBindNodeModel {
    @Override
    public void bindNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.bindNode(params),callBack);
    }
}
