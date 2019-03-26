package net.treebear.kwifimanager.mvp.server.model;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.SUserCover;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.PwdSignInContract;
import net.treebear.kwifimanager.util.RequestBodyUtils;

import okhttp3.RequestBody;

public class PwdSignInServerModel extends BaseServerModel implements PwdSignInContract.Model {
    @Override
    public void signInByVerifyPwd(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.signinByPassword(params), callBack);
    }

    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack) {
        ArrayMap<String, Object> stringObjectHashMap = new ArrayMap<>();
        stringObjectHashMap.put(Keys.FILTER, 1);
        bindObservable(mService.getUserInfoExt(RequestBodyUtils.convert(stringObjectHashMap)), callBack);
    }
}
