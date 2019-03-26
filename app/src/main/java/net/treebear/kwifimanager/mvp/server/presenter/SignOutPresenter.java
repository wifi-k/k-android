package net.treebear.kwifimanager.mvp.server.presenter;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.mvp.server.contract.SignOutContract;
import net.treebear.kwifimanager.mvp.server.model.SignOutModel;

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
