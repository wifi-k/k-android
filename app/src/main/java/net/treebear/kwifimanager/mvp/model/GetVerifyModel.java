package net.treebear.kwifimanager.mvp.model;

import net.treebear.kwifimanager.base.BaseModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.mvp.contract.GetVerifyContract;

import okhttp3.RequestBody;

public class GetVerifyModel extends BaseModel implements GetVerifyContract.IGetVerifyModel {
    @Override
    public void getGetVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }
}
