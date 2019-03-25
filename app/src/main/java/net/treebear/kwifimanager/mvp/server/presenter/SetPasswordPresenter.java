package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.SetPasswordContract;
import net.treebear.kwifimanager.mvp.server.model.SetPasswordServerModel;
import net.treebear.kwifimanager.util.SecurityUtils;

public class SetPasswordPresenter extends BasePresenter<SetPasswordContract.View, SetPasswordContract.Model> implements SetPasswordContract.Presenter {
    @Override
    public void setModel() {
        mModel = new SetPasswordServerModel();
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
