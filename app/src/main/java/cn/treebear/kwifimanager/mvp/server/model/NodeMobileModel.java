package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.mvp.server.contract.NodeMobileContract;
import okhttp3.RequestBody;

public class NodeMobileModel extends BaseServerModel implements NodeMobileContract.Model {
    @Override
    public void getNodeMobileList(RequestBody params, AsyncCallBack<BaseResponse<MobileListBean>> callBack) {
        bindObservable(mService.getNodeDeviceList(params), callBack);
    }

    @Override
    public void setNodeMobileInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeDevice(params), callBack);
    }
}
