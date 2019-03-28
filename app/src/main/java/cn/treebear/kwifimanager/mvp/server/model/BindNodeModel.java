package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.mvp.server.contract.BindNodeConstract;
import okhttp3.RequestBody;

public class BindNodeModel extends BaseServerModel implements BindNodeConstract.Model {
    @Override
    public void bindNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.bindNode(params), callBack);
    }
}
