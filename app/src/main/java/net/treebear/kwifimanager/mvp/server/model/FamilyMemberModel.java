package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.FamilyMemberCover;
import net.treebear.kwifimanager.mvp.server.contract.FamilyMemberContract;

import okhttp3.RequestBody;

public class FamilyMemberModel extends BaseServerModel implements FamilyMemberContract.Model {
    @Override
    public void getFamilyMembers(RequestBody params, AsyncCallBack<BaseResponse<FamilyMemberCover>> callBack) {
        bindObservable(mService.getFamilyMembers(params), callBack);
    }

    @Override
    public void modifyMember(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.setFamilyInfo(params), callBack);
    }

    @Override
    public void deleteMember(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.quitFamily(params), callBack);
    }
}
