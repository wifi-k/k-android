package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.SelectXiaoKContract;
import cn.treebear.kwifimanager.mvp.server.model.SelectXiaoKModel;
import cn.treebear.kwifimanager.util.Check;

public class SelectXiaoKPresenter extends BasePresenter<SelectXiaoKContract.View, SelectXiaoKContract.Model> implements SelectXiaoKContract.Presenter {
    @Override
    public void setModel() {
        mModel = new SelectXiaoKModel();
    }

    @Override
    public void getXiaoKList(int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.PAGE_SIZE);
        mModel.getNodeListAll(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<NodeInfoDetail>>() {
            @Override
            public void onSuccess(BaseResponse<NodeInfoDetail> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void selectXiaoK(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.selectXiaoK(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onSelectXiaoK(resultData);
                }
            }
        });
    }
}
