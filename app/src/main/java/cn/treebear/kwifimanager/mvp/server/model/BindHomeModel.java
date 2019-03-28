package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.mvp.server.contract.BindHomeContract;
import okhttp3.RequestBody;

public class BindHomeModel extends BaseServerModel implements BindHomeContract.Model {
    @Override
    public void getNodeList(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack) {
        bindObservable(mService.getNodeList(params), callBack);
    }
}
