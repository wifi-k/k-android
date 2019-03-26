package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.FamilyMemberCover;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.FamilyMemberContract;
import net.treebear.kwifimanager.mvp.server.model.FamilyMemberModel;
import net.treebear.kwifimanager.util.Check;

public class FamilyMemberPresenter extends BasePresenter<FamilyMemberContract.View, FamilyMemberContract.Model> implements FamilyMemberContract.Presenter {
    @Override
    public void setModel() {
        mModel = new FamilyMemberModel();
    }

    @Override
    public void getFamilyMembers(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.getFamilyMembers(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<FamilyMemberCover>>() {
            @Override
            public void onSuccess(BaseResponse<FamilyMemberCover> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void modifyMember(String nodeId, String userName) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.USER_NAME, userName);
        mModel.modifyMember(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.modifyMemberResponse(resultData);
                }
            }
        });
    }

    @Override
    public void deleteMember(String nodeId, long userId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.USER_ID, userId);
        mModel.deleteMember(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.deleteMemberResponse(resultData);
                }
            }
        });
    }
}
