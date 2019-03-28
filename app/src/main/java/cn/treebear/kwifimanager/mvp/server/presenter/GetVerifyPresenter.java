package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.GetVerifyContract;
import cn.treebear.kwifimanager.mvp.server.model.GetVerifyServerModel;
import cn.treebear.kwifimanager.util.Check;

public class GetVerifyPresenter extends BasePresenter<GetVerifyContract.View, GetVerifyContract.Model> implements GetVerifyContract.Presenter {
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
