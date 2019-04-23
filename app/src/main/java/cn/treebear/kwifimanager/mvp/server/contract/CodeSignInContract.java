package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface CodeSignInContract {

    interface View extends IView<String> {

        void onSignInOk(ServerUserInfo bean);

        void onSingInFail(BaseResponse response);

        void onUserInfoLoaded(SUserCover bean);

        void onUserInfoLoadFailed(BaseResponse response);
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
