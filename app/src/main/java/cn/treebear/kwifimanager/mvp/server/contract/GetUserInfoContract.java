package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;

public interface GetUserInfoContract {

    interface View extends IView<SUserCover> {
    }

    interface Presenter extends IPresenter<View> {

        void getUserInfo();
    }

    interface Model extends IModel {

        void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack);
    }

}
