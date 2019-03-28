package cn.treebear.kwifimanager.mvp.server.model;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;
import cn.treebear.kwifimanager.util.RequestBodyUtils;

public class GetUserInfoModel extends BaseServerModel implements GetUserInfoContract.Model {
    @Override
    public void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack) {
        ArrayMap<String, Object> stringObjectHashMap = new ArrayMap<>();
        stringObjectHashMap.put(Keys.FILTER, 1);
        bindObservable(mService.getUserInfoExt(RequestBodyUtils.convert(stringObjectHashMap)), callBack);
    }
}
