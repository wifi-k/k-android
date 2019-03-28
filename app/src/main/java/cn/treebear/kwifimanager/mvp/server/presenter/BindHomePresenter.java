package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.BindHomeContract;
import cn.treebear.kwifimanager.mvp.server.model.BindHomeModel;
import cn.treebear.kwifimanager.util.Check;

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
