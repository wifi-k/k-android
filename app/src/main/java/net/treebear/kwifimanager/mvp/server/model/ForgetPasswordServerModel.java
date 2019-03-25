package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.mvp.server.contract.ForgetPasswordContract;

import okhttp3.RequestBody;

public class ForgetPasswordServerModel extends BaseServerModel implements ForgetPasswordContract.Model {
    @Override
    public void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.forgetPassword(params), callBack);
    }
}
