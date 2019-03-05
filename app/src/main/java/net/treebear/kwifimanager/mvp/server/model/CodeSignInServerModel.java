package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.server.contract.CodeSignInContract;

import okhttp3.RequestBody;

public class CodeSignInServerModel extends BaseServerModel implements CodeSignInContract.ICodeSignInModel {
    @Override
    public void getSignInVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }

    @Override
    public void signInByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.signInByCode(params), callBack);
    }

    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.getUserInfo(), callBack);
    }
}
