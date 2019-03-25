package net.treebear.kwifimanager.mvp.server.presenter;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;
import net.treebear.kwifimanager.mvp.server.model.GetUserInfoModel;
import net.treebear.kwifimanager.util.Check;

public class GetUserInfoPresenter extends BasePresenter<GetUserInfoContract.View, GetUserInfoContract.Model> implements GetUserInfoContract.Presenter {
    @Override
    public void setModel() {
        mModel = new GetUserInfoModel();
    }

    @Override
    public void getUserInfo() {
        mModel.getUserInfo(new BaseAsyncCallback<BaseResponse<ServerUserInfo>>() {
            @Override
            public void onSuccess(BaseResponse<ServerUserInfo> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}
