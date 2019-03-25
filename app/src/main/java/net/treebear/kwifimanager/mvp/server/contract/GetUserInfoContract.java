package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

public interface GetUserInfoContract {

    interface View extends IView<ServerUserInfo>{}

    interface Presenter extends IPresenter<View>{

        void getUserInfo();
    }

    interface Model extends IModel{

        void getUserInfo(AsyncCallBack<BaseResponse<ServerUserInfo>> callBack);
    }

}
