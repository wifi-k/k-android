package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.mvp.server.contract.GetVerifyContract;

import okhttp3.RequestBody;

public class GetVerifyServerModel extends BaseServerModel implements GetVerifyContract.IGetVerifyModel {
    @Override
    public void getGetVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }
}
