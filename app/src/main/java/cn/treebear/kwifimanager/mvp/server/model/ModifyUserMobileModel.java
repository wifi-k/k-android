package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserMobileContract;
import okhttp3.RequestBody;

public class ModifyUserMobileModel extends BaseServerModel implements ModifyUserMobileContract.Model {
    @Override
    public void getVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }

    @Override
    public void modifyMobileByVerify(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.modifyUserMobile(params), callBack);
    }
}
