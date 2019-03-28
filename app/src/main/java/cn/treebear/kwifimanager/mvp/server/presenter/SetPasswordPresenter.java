package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.SetPasswordContract;
import cn.treebear.kwifimanager.mvp.server.model.SetPasswordServerModel;
import cn.treebear.kwifimanager.util.SecurityUtils;

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
