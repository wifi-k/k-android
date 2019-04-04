package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.ChildrenListContract;
import cn.treebear.kwifimanager.mvp.server.model.ChildrenListModel;

public class ChildrenListPresenter extends BasePresenter<ChildrenListContract.View, ChildrenListContract.Model> implements ChildrenListContract.Presenter {
    @Override
    public void setModel() {
        mModel = new ChildrenListModel();
    }

    @Override
    public void getChildrenList(String nodeId, int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.PAGE_SIZE);
        mModel.getChildrenList(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<ChildrenListBean>>() {
            @Override
            public void onSuccess(BaseResponse<ChildrenListBean> resultData) {
                if (mView != null && resultData != null) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}
