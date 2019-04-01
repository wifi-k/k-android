package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.TimeControlbean;
import cn.treebear.kwifimanager.mvp.server.contract.TimeControlContract;
import okhttp3.RequestBody;

public class TimeCotrolModel extends BaseServerModel implements TimeControlContract.Model {
    @Override
    public void getTimeControlPlan(RequestBody body, AsyncCallBack<BaseResponse<TimeControlbean>> callBack) {
        bindObservable(mService.getAllowDevice(body), callBack);
    }

    @Override
    public void setTimeControlPlan(RequestBody body, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setAllowDevice(body), callBack);
    }

    @Override
    public void deleteTimeControlPlan(RequestBody body, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.delAllowDevice(body), callBack);
    }
}
