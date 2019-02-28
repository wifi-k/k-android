package net.treebear.kwifimanager.mvp.model;

import net.treebear.kwifimanager.base.BaseModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.mvp.contract.PwdSignInContract;

import okhttp3.RequestBody;

public class PwdSignInModel extends BaseModel implements PwdSignInContract.IPwdSignInModel {
    @Override
    public void signInByVerifyPwd(RequestBody params, AsyncCallBack<BaseResponse<UserInfoBean>> callBack) {
        bindObservable(mService.SignInByPassword(params), callBack);
    }
}
