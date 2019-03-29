package cn.treebear.kwifimanager.mvp.server.model;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.QiNiuUserBean;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserInfoContract;
import cn.treebear.kwifimanager.util.RequestBodyUtils;
import okhttp3.RequestBody;

public class ModifyUserInfoModel extends BaseServerModel implements ModifyUserInfoContract.Model {

    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack) {
        ArrayMap<String, Object> stringObjectHashMap = new ArrayMap<>();
        stringObjectHashMap.put(Keys.FILTER, 1);
        bindObservable(mService.getUserInfoExt(RequestBodyUtils.convert(stringObjectHashMap)), callBack);
    }

    @Override
    public void getQiNiuToken(AsyncCallBack<BaseResponse<QiNiuUserBean>> callBack) {
        bindObservable(mService.getQiNiuToken(), callBack);
    }

    @Override
    public void setUserAvatar(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setUserInfo(params), callBack);
    }

}
