package net.treebear.kwifimanager.mvp.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.contract.SetPasswordContract;
import net.treebear.kwifimanager.mvp.model.SetPasswordModel;
import net.treebear.kwifimanager.util.SecurityUtils;

public class SetPasswordPresenter extends BasePresenter<SetPasswordContract.ISetPasswordView, SetPasswordContract.ISetPasswordModel> implements SetPasswordContract.ISetPasswordPresenter {
    @Override
    public void setModel() {
        mModel = new SetPasswordModel();
    }

    @Override
    public void setPassword(String passwd) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.PASSWORD, SecurityUtils.md5(passwd));
        mModel.setPassword(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onLoadData(0);
                }
            }
        });

    }
}
