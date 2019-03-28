package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.mvp.server.contract.SelectXiaoKContract;
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
