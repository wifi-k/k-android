package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.mvp.server.contract.UnbindHomeContract;
import okhttp3.RequestBody;

public class UnbindHomeModel extends BaseServerModel implements UnbindHomeContract.Model {
    @Override
    public void joinFamily(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.joinFamilyByCode(params), callBack);
    }
}
