package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.mvp.server.contract.SetPasswordContract;

import okhttp3.RequestBody;

public class SetPasswordServerModel extends BaseServerModel implements SetPasswordContract.ISetPasswordModel {
    @Override
    public void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setUserPassword(params), callBack);
    }
}
