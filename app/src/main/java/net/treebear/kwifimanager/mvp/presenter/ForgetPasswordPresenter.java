package net.treebear.kwifimanager.mvp.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.contract.ForgetPasswordContract;
import net.treebear.kwifimanager.mvp.model.ForgetPasswordModel;
import net.treebear.kwifimanager.util.SecurityUtils;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.IForgetPasswordView, ForgetPasswordContract.IForgetPasswordModel> implements ForgetPasswordContract.IForgetPasswordPresenter {
    @Override
    public void setModel() {
        mModel = new ForgetPasswordModel();
    }

    @Override
    public void setPassword(String mobile, String vcode, String passwd) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.VERIFY_CODE, vcode);
        map.put(Keys.PASSWORD, SecurityUtils.md5(passwd));
        mModel.setPassword(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onLoadData(resultData);
                }
            }
        });
    }
}
