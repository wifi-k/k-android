package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

public interface GetUserInfoContract {

    interface IGetUserInfoView extends IView<ServerUserInfo>{}

    interface IGetUserInfoPresenter extends IPresenter<IGetUserInfoView>{

        void getUserInfo();
    }

    interface IGetUserInfoModel extends IModel{

        void getUserInfo(AsyncCallBack<BaseResponse<ServerUserInfo>> callBack);
    }

}
