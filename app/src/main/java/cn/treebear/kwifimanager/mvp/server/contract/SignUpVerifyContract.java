package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
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
