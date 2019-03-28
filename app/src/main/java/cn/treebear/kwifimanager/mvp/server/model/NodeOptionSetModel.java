package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.NodeWifiListBean;
import cn.treebear.kwifimanager.mvp.server.contract.NodeOptionSetContract;
import okhttp3.RequestBody;

public class NodeOptionSetModel extends BaseServerModel implements NodeOptionSetContract.Model {
    @Override
    public void getNodeSsid(RequestBody params, AsyncCallBack<BaseResponse<NodeWifiListBean>> callBack) {
        bindObservable(mService.getNodeSSIDList(params), callBack);
    }

    @Override
    public void modifySsid(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeSsid(params), callBack);
    }

    @Override
    public void modifyPasswd(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeSsid(params), callBack);
    }
}
