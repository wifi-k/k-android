package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.UnbindHomeContract;
import net.treebear.kwifimanager.mvp.server.model.UnbindHomeModel;

public class UnbindHomePresenter extends BasePresenter<UnbindHomeContract.View, UnbindHomeContract.Model> implements UnbindHomeContract.Presenter {
    @Override
    public void setModel() {
        mModel = new UnbindHomeModel();
    }

    @Override
    public void joinFamily(String inviteCode) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.INVITE_CODE, inviteCode);
        mModel.joinFamily(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onLoadData(resultData);
                }
            }
        });

    }
}
