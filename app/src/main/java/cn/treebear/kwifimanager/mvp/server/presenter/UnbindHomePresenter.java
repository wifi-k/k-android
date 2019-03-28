package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.UnbindHomeContract;
import cn.treebear.kwifimanager.mvp.server.model.UnbindHomeModel;

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
