package net.treebear.kwifimanager.mvp.wifi.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.GetVerifyContract;
import net.treebear.kwifimanager.mvp.server.model.GetVerifyServerModel;
import net.treebear.kwifimanager.util.Check;

public class GetVerifyPresenter extends BasePresenter<GetVerifyContract.IGetVerifyView, GetVerifyContract.IGetVerifyModel> implements GetVerifyContract.IGetVerifyPresenter {
    @Override
    public void setModel() {
        mModel = new GetVerifyServerModel();
    }

    @Override
    public void getGetVerifyCode(int type, String mobile) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.TYPE, type);
        map.put(Keys.MOBILE, mobile);
        mModel.getGetVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}
