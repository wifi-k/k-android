package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.mvp.server.contract.SignOutContract;

public class SignOutModel extends BaseServerModel implements SignOutContract.Model {
    @Override
    public void signOut(AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.userQuit(),callBack);
    }
}
