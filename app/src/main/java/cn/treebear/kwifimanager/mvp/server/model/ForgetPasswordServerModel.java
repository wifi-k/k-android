package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.mvp.server.contract.ForgetPasswordContract;
import okhttp3.RequestBody;

public class ForgetPasswordServerModel extends BaseServerModel implements ForgetPasswordContract.Model {
    @Override
    public void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.forgetPassword(params), callBack);
    }
}
