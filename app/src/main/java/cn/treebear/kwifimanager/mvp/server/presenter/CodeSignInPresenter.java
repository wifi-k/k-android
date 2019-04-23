package cn.treebear.kwifimanager.mvp.server.presenter;

import android.os.Build;
import android.util.ArrayMap;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
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
        map.put(Keys.DEV_TOKEN, MyApplication.getAppContext().getDevToken());
        map.put(Keys.DEV_TYPE, Values.ANDROID);
        map.put(Keys.DEV_OS, String.format("%s %s", Build.MODEL, Build.VERSION.RELEASE));
        mModel.signInByVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<ServerUserInfo>>() {
            @Override
            public void onSuccess(BaseResponse<ServerUserInfo> resultData) {
                if (mView != null) {
                    mView.onSignInOk(resultData.getData());
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onSingInFail(resultData);
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

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onUserInfoLoadFailed(resultData);
                }
            }
        });
    }
}
