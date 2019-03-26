package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

public interface SignOutContract {

    interface View extends IView<BaseResponse> {
        void onSignOut();
    }

    interface Presenter extends IPresenter<View> {

        void signOut();
    }

    interface Model extends IModel {
        void signOut(AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
