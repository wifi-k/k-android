package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.mvp.server.contract.SignUpVerifyContract;
import okhttp3.RequestBody;


/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public class SignUpVerifyServerModel extends BaseServerModel implements SignUpVerifyContract.Model {

    @Override
    public void getSignUpVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }

    @Override
    public void signUpByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.signUpByVerifyCode(params), callBack);
    }
}
