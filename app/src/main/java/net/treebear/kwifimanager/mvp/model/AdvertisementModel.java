package net.treebear.kwifimanager.mvp.model;

import net.treebear.kwifimanager.bean.AdvertisementBean;
import net.treebear.kwifimanager.mvp.contract.AdvertisementContract;

import net.treebear.kwifimanager.base.BaseModel;
import net.treebear.kwifimanager.base.BaseResponse;

import java.util.HashMap;


/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public class AdvertisementModel extends BaseModel implements AdvertisementContract.IAdvertisementModel {

    @Override
    public void getAdvertisement(HashMap<String, Object> params, AsyncCallBack<BaseResponse<AdvertisementBean>> callBack) {
        bindObservable(mService.getAdvertisement(params), callBack);
    }
}
