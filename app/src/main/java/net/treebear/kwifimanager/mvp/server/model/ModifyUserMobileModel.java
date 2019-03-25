package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.mvp.server.contract.ModifyUserMobileContract;

import okhttp3.RequestBody;

public class ModifyUserMobileModel extends BaseServerModel implements ModifyUserMobileContract.Model {
    @Override
    public void getVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {

    }

    @Override
    public void modifyMobileByVerify(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {

    }
}
