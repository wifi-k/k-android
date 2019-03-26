package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.mvp.server.contract.UnbindHomeContract;

import okhttp3.RequestBody;

public class UnbindHomeModel extends BaseServerModel implements UnbindHomeContract.Model {
    @Override
    public void joinFamily(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.joinFamilyByCode(params), callBack);
    }
}
