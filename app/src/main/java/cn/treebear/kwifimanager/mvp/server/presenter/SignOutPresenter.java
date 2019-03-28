package cn.treebear.kwifimanager.mvp.server.presenter;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.mvp.server.contract.SignOutContract;
import cn.treebear.kwifimanager.mvp.server.model.SignOutModel;

public class SignOutPresenter extends BasePresenter<SignOutContract.View, SignOutContract.Model> implements SignOutContract.Presenter {
    @Override
    public void setModel() {
        mModel = new SignOutModel();
    }

    @Override
    public void signOut() {
        mModel.signOut(new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onSignOut();
                }
            }
        });
    }
}
