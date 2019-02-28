package net.treebear.kwifimanager.mvp.model;

import net.treebear.kwifimanager.base.BaseModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.mvp.contract.ForgetPasswordContract;

import okhttp3.RequestBody;

public class ForgetPasswordModel extends BaseModel implements ForgetPasswordContract.IForgetPasswordModel {
    @Override
    public void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.forgetPassword(params),callBack);
    }
}
