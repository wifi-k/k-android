package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.server.contract.PwdSignInContract;

import okhttp3.RequestBody;

public class PwdSignInServerModel extends BaseServerModel implements PwdSignInContract.Model {
    @Override
    public void signInByVerifyPwd(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.signinByPassword(params), callBack);
    }

    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.getUserInfo(), callBack);
    }
}
