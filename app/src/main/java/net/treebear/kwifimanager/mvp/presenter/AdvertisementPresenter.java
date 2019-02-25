package net.treebear.kwifimanager.mvp.presenter;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.mvp.contract.AdvertisementContract;
import net.treebear.kwifimanager.mvp.model.AdvertisementModel;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.AdvertisementBean;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.TLog;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public class AdvertisementPresenter extends BasePresenter<AdvertisementContract.IAdvertisementView, AdvertisementContract.IAdvertisementModel> implements AdvertisementContract.IAdvertisementPresenter {

    @Override
    public void getAdvertisement() {
        mModel.getAdvertisement(getBaseParams(), new BaseAsyncCallback<BaseResponse<AdvertisementBean>>() {
            @Override
            public void onSuccess(BaseResponse<AdvertisementBean> resultData) {
                TLog.i("(AdvertisementPresenter.java:37) ->" + resultData.toString());
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getResultData());
                }
            }
        });
    }

    @Override
    public void setModel() {
        mModel = new AdvertisementModel();
    }
}
