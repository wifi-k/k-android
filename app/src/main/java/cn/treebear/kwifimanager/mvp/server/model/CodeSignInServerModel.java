package cn.treebear.kwifimanager.mvp.server.model;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.CodeSignInContract;
import cn.treebear.kwifimanager.util.RequestBodyUtils;
import okhttp3.RequestBody;

public class CodeSignInServerModel extends BaseServerModel implements CodeSignInContract.Model {
    @Override
    public void getSignInVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack) {
        bindObservable(mService.getVerifyByType(params), callBack);
    }

    @Override
    public void signInByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack) {
        bindObservable(mService.signInByCode(params), callBack);
    }

    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack) {
        ArrayMap<String, Object> stringObjectHashMap = new ArrayMap<>();
        stringObjectHashMap.put(Keys.FILTER, 1);
        bindObservable(mService.getUserInfoExt(RequestBodyUtils.convert(stringObjectHashMap)), callBack);
    }
}
