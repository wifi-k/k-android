package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface PwdSignInContract {

    interface View extends IView<ServerUserInfo> {

        void onnUserInfoLoaded(SUserCover bean);

    }

    interface Presenter extends IPresenter<View> {

        void signInByPwd(String mobile, String pwd);

        void getUserInfo();

    }

    interface Model extends IModel {

        void signInByVerifyPwd(RequestBody params, AsyncCallBack<BaseResponse<ServerUserInfo>> callBack);

        void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack);
    }
}
