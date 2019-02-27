package net.treebear.kwifimanager.mvp.model;

import net.treebear.kwifimanager.base.BaseModel;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.mvp.contract.SetPasswordContract;

import okhttp3.RequestBody;

public class SetPasswordModel extends BaseModel implements SetPasswordContract.ISetPasswordModel {
    @Override
    public void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setUserPassword(params), callBack);
    }
}
