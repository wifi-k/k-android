package net.treebear.kwifimanager.mvp.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.contract.CodeSignInContract;
import net.treebear.kwifimanager.mvp.model.CodeSignInModel;
import net.treebear.kwifimanager.util.Check;

public class CodeSignInPresenter extends BasePresenter<CodeSignInContract.ICodeSignInView, CodeSignInContract.ICodeSignInModel>
        implements CodeSignInContract.ICodeSignInPresenter {
    @Override
    public void setModel() {
        mModel = new CodeSignInModel();
    }

    @Override
    public void getSignInVerifyCode(String mobile) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE,mobile);
        map.put(Keys.TYPE, Config.RequestType.VERIFY_CODE_SIGN_IN);
        mModel.getSignInVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> resultData) {
                if (Check.hasContent(resultData,mView)){
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void signInByVerifyCode(String mobile, String code) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE,mobile);
        map.put(Keys.VERIFY_CODE,code);
        mModel.signInByVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<UserInfoBean>>() {
            @Override
            public void onSuccess(BaseResponse<UserInfoBean> resultData) {
                if (Check.hasContent(resultData,mView)){
                    mView.onSignInOk(resultData.getData());
                }
            }
        });
    }
}
