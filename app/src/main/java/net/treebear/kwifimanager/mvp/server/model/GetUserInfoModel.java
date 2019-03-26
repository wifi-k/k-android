package net.treebear.kwifimanager.mvp.server.model;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.SUserCover;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;
import net.treebear.kwifimanager.util.RequestBodyUtils;

public class GetUserInfoModel extends BaseServerModel implements GetUserInfoContract.Model {
    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack) {
        ArrayMap<String, Object> stringObjectHashMap = new ArrayMap<>();
        stringObjectHashMap.put(Keys.FILTER, 1);
        bindObservable(mService.getUserInfoExt(RequestBodyUtils.convert(stringObjectHashMap)), callBack);
    }
}
