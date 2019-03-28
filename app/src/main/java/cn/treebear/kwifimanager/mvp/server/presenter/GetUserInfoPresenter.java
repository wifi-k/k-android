package cn.treebear.kwifimanager.mvp.server.presenter;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;
import cn.treebear.kwifimanager.mvp.server.model.GetUserInfoModel;
import cn.treebear.kwifimanager.util.Check;

public class GetUserInfoPresenter extends BasePresenter<GetUserInfoContract.View, GetUserInfoContract.Model> implements GetUserInfoContract.Presenter {
    @Override
    public void setModel() {
        mModel = new GetUserInfoModel();
    }

    @Override
    public void getUserInfo() {
        mModel.getUserInfo(new BaseAsyncCallback<BaseResponse<SUserCover>>() {
            @Override
            public void onSuccess(BaseResponse<SUserCover> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}