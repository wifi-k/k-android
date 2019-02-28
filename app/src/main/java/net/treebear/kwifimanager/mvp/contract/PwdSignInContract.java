package net.treebear.kwifimanager.mvp.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface PwdSignInContract {

    interface IPwdSignInView extends IView<UserInfoBean> {

    }

    interface IPwdSignInPresenter extends IPresenter<IPwdSignInView> {

        void signInByPwd(String mobile, String pwd);
    }

    interface IPwdSignInModel extends IModel {

        void signInByVerifyPwd(RequestBody params, AsyncCallBack<BaseResponse<UserInfoBean>> callBack);
    }
}
