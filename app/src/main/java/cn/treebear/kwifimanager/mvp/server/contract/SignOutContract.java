package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;

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
