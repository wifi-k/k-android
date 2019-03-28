package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.CodeSignInContract;
import cn.treebear.kwifimanager.mvp.server.model.CodeSignInServerModel;
import cn.treebear.kwifimanager.util.Check;

public class CodeSignInPresenter extends BasePresenter<CodeSignInContract.View, CodeSignInContract.Model>
        implements CodeSignInContract.Presenter {
    @Override
    public void setModel() {
        mModel = new CodeSignInServerModel();
    }

    @Override
    public void getSignInVerifyCode(String mobile) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.TYPE, Config.RequestType.VERIFY_CODE_SIGN_IN);
        mModel.getSignInVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void signInByVerifyCode(String mobile, String code) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.VERIFY_CODE, code);
        mModel.signInByVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<ServerUserInfo>>() {
            @Override
            public void onSuccess(BaseResponse<ServerUserInfo> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onSignInOk(resultData.getData());
                }
            }
        });
    }

    @Override
    public void getUserInfo() {
        mModel.getUserInfo(new BaseAsyncCallback<BaseResponse<SUserCover>>() {
            @Override
            public void onSuccess(BaseResponse<SUserCover> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onUserInfoLoaded(resultData.getData());
                }
            }
        });
    }
}
