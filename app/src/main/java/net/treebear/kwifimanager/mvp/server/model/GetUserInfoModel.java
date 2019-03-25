package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;

public class GetUserInfoModel extends BaseServerModel implements GetUserInfoContract.Model {
    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.getUserInfo(),callBack);
    }
}
