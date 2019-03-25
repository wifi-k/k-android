package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.QiNiuUserBean;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface ModifyUserInfoContract {

    interface View extends IView<Object> {

        void onQiNiuTokenResponse(QiNiuUserBean result);

        void onModifyUserInfo();
    }

    interface Presenter extends IPresenter<View> {
        /**
         * 获取七牛云token
         */
        void getQiNiuToken();

        void modifyUserInfo(String name, String picUrl);
    }

    interface Model extends IModel {
        /**
         * 获取七牛云token
         */
        void getQiNiuToken(AsyncCallBack<BaseResponse<QiNiuUserBean>> callBack);

        void setUserAvatar(RequestBody params,AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
