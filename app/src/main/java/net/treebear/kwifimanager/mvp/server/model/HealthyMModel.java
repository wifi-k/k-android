package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.HealthyModelBean;
import net.treebear.kwifimanager.mvp.server.contract.HealthyModelContract;

import okhttp3.RequestBody;

public class HealthyMModel extends BaseServerModel implements HealthyModelContract.Model {
    @Override
    public void getHealthyModelInfo(RequestBody params, AsyncCallBack<BaseResponse<HealthyModelBean>> callBack) {
        bindObservable(mService.getHealthyModelInfo(params),callBack);
    }

    @Override
    public void setHealthyModelInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setHealthyModelInfo(params),callBack);
    }
}
