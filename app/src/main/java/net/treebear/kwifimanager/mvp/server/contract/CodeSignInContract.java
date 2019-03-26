package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.SUserCover;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface CodeSignInContract {

    interface View extends IView<String> {

        void onSignInOk(ServerUserInfo bean);

        void onUserInfoLoaded(SUserCover bean);
    }

    interface Presenter extends IPresenter<View> {

        void getSignInVerifyCode(String mobile);

        void signInByVerifyCode(String mobile, String code);

        void getUserInfo();
    }

    interface Model extends IModel {

        void getSignInVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack);

        void signInByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack);

        void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack);
    }
}
