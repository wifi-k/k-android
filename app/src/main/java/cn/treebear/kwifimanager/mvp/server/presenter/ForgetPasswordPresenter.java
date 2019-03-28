package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.ForgetPasswordContract;
import cn.treebear.kwifimanager.mvp.server.model.ForgetPasswordServerModel;
import cn.treebear.kwifimanager.util.SecurityUtils;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.View, ForgetPasswordContract.Model> implements ForgetPasswordContract.Presenter {
    @Override
    public void setModel() {
        mModel = new ForgetPasswordServerModel();
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
