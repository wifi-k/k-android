package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.BindHomeContract;
import net.treebear.kwifimanager.mvp.server.model.BindHomeModel;
import net.treebear.kwifimanager.util.Check;

public class BindHomePresenter extends BasePresenter<BindHomeContract.View, BindHomeContract.Model> implements BindHomeContract.Presenter {
    @Override
    public void setModel() {
        mModel = new BindHomeModel();
    }

    @Override
    public void getNodeList() {
        ArrayMap<String, Object> map = map();
        map.put(Keys.PAGE_NO, 1);
        map.put(Keys.PAGE_SIZE, 1);
        mModel.getNodeList(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<NodeInfoDetail>>() {
            @Override
            public void onSuccess(BaseResponse<NodeInfoDetail> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });


    }
}
