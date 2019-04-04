package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.mvp.server.contract.BindHomeContract;
import okhttp3.RequestBody;

public class BindHomeModel extends BaseServerModel implements BindHomeContract.Model {
    @Override
    public void getNodeList(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack) {
        bindObservable(mService.getNodeList(params), callBack);
    }

    @Override
    public void getMessageList(RequestBody params, AsyncCallBack<BaseResponse<MessageInfoBean>> callBack) {
        bindObservable(mService.getMessageList(params), callBack);
    }

    @Override
    public void setMobileInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeDevice(params), callBack);
    }

    @Override
    public void getMobileList(RequestBody params, AsyncCallBack<BaseResponse<MobileListBean>> callBack) {
        bindObservable(mService.getNodeDeviceList(params), callBack);
    }

    @Override
    public void getChildrenList(RequestBody params, AsyncCallBack<BaseResponse<ChildrenListBean>> callBack) {
        bindObservable(mService.getChildrenList(params),callBack);
    }
}
