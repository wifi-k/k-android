package net.treebear.kwifimanager.mvp.model;

import net.treebear.kwifimanager.base.BaseModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.mvp.contract.CodeSignInContract;

import okhttp3.RequestBody;

public class CodeSignInModel extends BaseModel implements CodeSignInContract.ICodeSignInModel {
    @Override
    public void getSignInVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }

    @Override
    public void signInByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<UserInfoBean>> callBack) {
        bindObservable(mService.signInByCode(params), callBack);
    }
}
