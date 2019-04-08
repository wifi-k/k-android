package cn.treebear.kwifimanager.mvp.server.presenter;

import android.os.Build;
import android.util.ArrayMap;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.mvp.server.contract.SignUpVerifyContract;
import cn.treebear.kwifimanager.mvp.server.model.SignUpVerifyServerModel;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.TLog;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public class SignUpVerifyPresenter extends BasePresenter<SignUpVerifyContract.View, SignUpVerifyContract.Model> implements SignUpVerifyContract.Presenter {

    @Override
    public void setModel() {
        mModel = new SignUpVerifyServerModel();
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
        map.put(Keys.DEV_TOKEN, MyApplication.getAppContext().getDevToken());
        map.put(Keys.DEV_TYPE, Values.ANDROID);
        map.put(Keys.DEV_OS, String.format("%s %s", Build.MODEL, Build.VERSION.RELEASE));
        mModel.signUpByVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<ServerUserInfo>>() {
            @Override
            public void onSuccess(BaseResponse<ServerUserInfo> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onSignUpOk(resultData.getData());
                }
            }
        });
    }
}
