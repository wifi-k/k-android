package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.BindNodeConstract;
import cn.treebear.kwifimanager.mvp.server.model.BindNodeModel;

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
