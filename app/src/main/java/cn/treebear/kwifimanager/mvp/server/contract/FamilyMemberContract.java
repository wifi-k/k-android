package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.FamilyMemberCover;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
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
