package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface UnbindHomeContract {

    interface View extends IView<BaseResponse> {
    }

    interface Presenter extends IPresenter<View> {

        void joinFamily(String inviteCode);
    }

    interface Model extends IModel {
        void joinFamily(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
