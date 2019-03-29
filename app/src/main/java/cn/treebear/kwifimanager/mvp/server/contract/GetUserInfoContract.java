package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface GetUserInfoContract {

    interface View extends IView<SUserCover> {
        void onJoinFamilySuccess();

        void onJoinFamilyFailed(BaseResponse response);
    }

    interface Presenter extends IPresenter<View> {

        void getUserInfo();

        void joinFamily(String inviteCode);
    }

    interface Model extends IModel {

        void getUserInfo(AsyncCallBack<BaseResponse<SUserCover>> callBack);

        void joinFamily(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
