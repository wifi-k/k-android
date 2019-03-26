package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.FamilyMemberCover;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface FamilyMemberContract {

    interface View extends IView<FamilyMemberCover> {

        void modifyMemberResponse(BaseResponse response);

        void deleteMemberResponse(BaseResponse response);
    }

    interface Presenter extends IPresenter<View> {

        void getFamilyMembers(String nodeId);

        void modifyMember(String nodeId, String userName);

        void deleteMember(String nodeId, long userId);

    }

    interface Model extends IModel {
        void getFamilyMembers(RequestBody params, AsyncCallBack<BaseResponse<FamilyMemberCover>> callBack);

        void modifyMember(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void deleteMember(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }

}
