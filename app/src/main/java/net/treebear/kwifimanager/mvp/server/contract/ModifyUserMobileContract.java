package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface ModifyUserMobileContract {

    interface View extends IView<Object> {
    }

    interface Presenter extends IPresenter<View> {

        void getVerifyCode(String mobile);

        void modifyMobileByVerify(String mobile,String code);
    }

    interface Model extends IModel {

        void getVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void modifyMobileByVerify(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

    }
}
