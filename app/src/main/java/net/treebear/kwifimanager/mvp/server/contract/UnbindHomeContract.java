package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface UnbindHomeContract {

    interface View extends IView<BaseResponse> {}

    interface Presenter extends IPresenter<View>{

        void joinFamily(String inviteCode);
    }

    interface Model extends IModel{
        void joinFamily(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
