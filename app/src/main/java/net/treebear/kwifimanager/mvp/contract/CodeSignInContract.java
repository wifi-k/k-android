package net.treebear.kwifimanager.mvp.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface CodeSignInContract {

    interface ICodeSignInView extends IView<String> {

        void onSignInOk(UserInfoBean bean);

        void onUserInfoLoaded(UserInfoBean bean);
    }

    interface ICodeSignInPresenter extends IPresenter<ICodeSignInView> {

        void getSignInVerifyCode(String mobile);

        void signInByVerifyCode(String mobile, String code);

        void getUserInfo();
    }

    interface ICodeSignInModel extends IModel {

        void getSignInVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack);

        void signInByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<UserInfoBean>> callBack);

        void getUserInfo(AsyncCallBack<BaseResponse<UserInfoBean>> callBack);
    }
}
