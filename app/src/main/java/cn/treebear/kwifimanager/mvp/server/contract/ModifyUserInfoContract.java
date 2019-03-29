package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.QiNiuUserBean;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface ModifyUserInfoContract {

    interface View extends IView<SUserCover> {

        void onQiNiuTokenResponse(QiNiuUserBean result);

        void onModifyUserInfo();
    }

    interface Presenter extends IPresenter<View> {
        void getUserInfo();

        /**
         * 获取七牛云token
         */
        void getQiNiuToken();

        void modifyUserInfo(String name, String picUrl);
    }

    interface Model extends IModel {
        void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack);

        /**
         * 获取七牛云token
         */
        void getQiNiuToken(AsyncCallBack<BaseResponse<QiNiuUserBean>> callBack);

        void setUserAvatar(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
