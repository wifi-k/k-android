package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.QiNiuUserBean;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserInfoContract;
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
