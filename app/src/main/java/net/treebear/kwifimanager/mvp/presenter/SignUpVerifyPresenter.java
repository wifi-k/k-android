package net.treebear.kwifimanager.mvp.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.contract.SignUpVerifyContract;
import net.treebear.kwifimanager.mvp.model.SignUpVerifyModel;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.TLog;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public class SignUpVerifyPresenter extends BasePresenter<SignUpVerifyContract.ISignUpVerifyView, SignUpVerifyContract.ISignUpVerifyModel> implements SignUpVerifyContract.ISignUpVerifyPresenter {

    @Override
    public void setModel() {
        mModel = new SignUpVerifyModel();
    }

    @Override
    public void getSignUpVerifyCode(String mobile) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.TYPE, 1);
        mModel.getSignUpVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> resultData) {
                TLog.i("(SignUpVerifyPresenter.java:37) ->" + resultData.toString());
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void signUpByVerifyCode(String mobile, String code) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.VERIFY_CODE, code);
        mModel.signUpByVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<UserInfoBean>>() {
            @Override
            public void onSuccess(BaseResponse<UserInfoBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onSignUpOk(resultData.getData());
                }
            }
        });
    }
}
