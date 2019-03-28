package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.mvp.server.contract.GetVerifyContract;
import okhttp3.RequestBody;

public class GetVerifyServerModel extends BaseServerModel implements GetVerifyContract.Model {
    @Override
    public void getGetVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }
}
