package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.mvp.server.contract.SignOutContract;

public class SignOutModel extends BaseServerModel implements SignOutContract.Model {
    @Override
    public void signOut(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.userQuit(), callBack);
    }
}
