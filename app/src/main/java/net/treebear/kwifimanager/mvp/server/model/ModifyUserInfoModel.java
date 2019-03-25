package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.QiNiuUserBean;
import net.treebear.kwifimanager.mvp.server.contract.ModifyUserInfoContract;

import okhttp3.RequestBody;

public class ModifyUserInfoModel extends BaseServerModel implements ModifyUserInfoContract.Model {
    @Override
    public void getQiNiuToken(AsyncCallBack<BaseResponse<QiNiuUserBean>> callBack) {
        bindObservable(mService.getQiNiuToken(), callBack);
    }

    @Override
    public void setUserAvatar(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setUserInfo(params), callBack);
    }

}
