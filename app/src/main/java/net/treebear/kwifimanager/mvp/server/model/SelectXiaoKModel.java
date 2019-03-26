package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.mvp.server.contract.SelectXiaoKContract;

import okhttp3.RequestBody;

public class SelectXiaoKModel extends BaseServerModel implements SelectXiaoKContract.Model {
    @Override
    public void getNodeListAll(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack) {
        bindObservable(mService.getNodeList(params), callBack);
    }

    @Override
    public void selectXiaoK(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.selectNode(params), callBack);
    }
}
