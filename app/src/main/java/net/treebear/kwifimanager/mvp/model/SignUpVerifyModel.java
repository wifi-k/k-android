package net.treebear.kwifimanager.mvp.model;

import net.treebear.kwifimanager.base.BaseModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.mvp.contract.SignUpVerifyContract;

import okhttp3.RequestBody;


/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public class SignUpVerifyModel extends BaseModel implements SignUpVerifyContract.ISignUpVerifyModel {

    @Override
    public void getSignUpVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getSignUpVerify(params), callBack);
    }

    @Override
    public void signUpByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<UserInfoBean>> callBack) {
        bindObservable(mService.signUpByVerifyCode(params), callBack);
    }
}
