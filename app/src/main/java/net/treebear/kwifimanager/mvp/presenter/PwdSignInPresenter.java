package net.treebear.kwifimanager.mvp.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.contract.PwdSignInContract;
import net.treebear.kwifimanager.mvp.model.PwdSignInModel;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.SecurityUtils;

public class PwdSignInPresenter extends BasePresenter<PwdSignInContract.IPwdSignInView, PwdSignInContract.IPwdSignInModel>
        implements PwdSignInContract.IPwdSignInPresenter {
    @Override
    public void setModel() {
        mModel = new PwdSignInModel();
    }

    @Override
    public void signInByPwd(String mobile, String pwd) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.PASSWORD, SecurityUtils.md5(pwd));
        mModel.signInByVerifyPwd(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<UserInfoBean>>() {
            @Override
            public void onSuccess(BaseResponse<UserInfoBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void getUserInfo() {
        mModel.getUserInfo(new BaseAsyncCallback<BaseResponse<UserInfoBean>>() {
            @Override
            public void onSuccess(BaseResponse<UserInfoBean> resultData) {
                if (Check.hasContent(resultData,mView)){
                    mView.onnUserInfoLoaded(resultData.getData());
                }
            }
        });
    }
}
