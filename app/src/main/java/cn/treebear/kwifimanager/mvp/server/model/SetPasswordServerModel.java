package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.mvp.server.contract.SetPasswordContract;
import okhttp3.RequestBody;

public class SetPasswordServerModel extends BaseServerModel implements SetPasswordContract.Model {
    @Override
    public void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setUserPassword(params), callBack);
    }
}
