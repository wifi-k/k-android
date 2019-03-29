package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import okhttp3.RequestBody;

public class AllMobileListModel extends BaseServerModel implements AllMobileListContract.Model {

    @Override
    public void setMobileInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setNodeDevice(params), callBack);
    }

    @Override
    public void getMobileList(RequestBody params, AsyncCallBack<BaseResponse<MobileListBean>> callBack) {
        bindObservable(mService.getNodeDeviceList(params), callBack);
    }
}
