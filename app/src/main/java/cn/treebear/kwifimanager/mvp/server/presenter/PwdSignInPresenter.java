package cn.treebear.kwifimanager.mvp.server.presenter;

import android.os.Build;
import android.util.ArrayMap;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.mvp.server.contract.PwdSignInContract;
import cn.treebear.kwifimanager.mvp.server.model.PwdSignInServerModel;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.SecurityUtils;

public class PwdSignInPresenter extends BasePresenter<PwdSignInContract.View, PwdSignInContract.Model>
        implements PwdSignInContract.Presenter {

    @Override
    public void setModel() {
        mModel = new PwdSignInServerModel();
    }

    @Override
    public void signInByPwd(String mobile, String pwd) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.PASSWORD, SecurityUtils.md5(pwd));
        map.put(Keys.DEV_TOKEN, MyApplication.getAppContext().getDevToken());
        map.put(Keys.DEV_TYPE, Values.ANDROID);
        map.put(Keys.DEV_OS, String.format("%s %s", Build.MODEL, Build.VERSION.RELEASE));
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
        mModel.getUserInfo(new BaseAsyncCallback<BaseResponse<SUserCover>>() {
            @Override
            public void onSuccess(BaseResponse<SUserCover> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onnUserInfoLoaded(resultData.getData());
                }
            }
        });
    }

}
