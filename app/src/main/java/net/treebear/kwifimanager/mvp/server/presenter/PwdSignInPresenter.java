package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.PwdSignInContract;
import net.treebear.kwifimanager.mvp.server.model.PwdSignInServerModel;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.SecurityUtils;

public class PwdSignInPresenter extends BasePresenter<PwdSignInContract.IPwdSignInView, PwdSignInContract.IPwdSignInModel>
        implements PwdSignInContract.IPwdSignInPresenter {
    @Override
    public void setModel() {
        mModel = new PwdSignInServerModel();
    }

    @Override
    public void signInByPwd(String mobile, String pwd) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.PASSWORD, SecurityUtils.md5(pwd));
        mModel.signInByVerifyPwd(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<ServerUserInfo>>() {
            @Override
            public void onSuccess(BaseResponse<ServerUserInfo> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void getUserInfo() {
        mModel.getUserInfo(new BaseAsyncCallback<BaseResponse<ServerUserInfo>>() {
            @Override
            public void onSuccess(BaseResponse<ServerUserInfo> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onnUserInfoLoaded(resultData.getData());
                }
            }
        });
    }
}
