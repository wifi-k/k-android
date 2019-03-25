package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */
public interface SignUpVerifyContract {

    interface View extends IView<String> {

        void onSignUpOk(ServerUserInfo bean);

    }

    interface Presenter extends IPresenter<View> {

        void getSignUpVerifyCode(String mobile);

        void signUpByVerifyCode(String mobile, String code);
    }

    interface Model extends IModel {

        void getSignUpVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack);

        void signUpByVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack);
    }
}
