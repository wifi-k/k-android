package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.BindNodeConstract;
import net.treebear.kwifimanager.mvp.server.model.BindNodeModel;

public class BindNodePresenter extends BasePresenter<BindNodeConstract.View, BindNodeConstract.Model> implements BindNodeConstract.Presenter {
    @Override
    public void setModel() {
        mModel = new BindNodeModel();
    }

    @Override
    public void bindNode(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.bindNode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onLoadData(resultData);
                }
            }
        });
    }
}
