package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.http.ApiCode;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.MyNodeContract;
import net.treebear.kwifimanager.mvp.server.model.MyNodeModel;
import net.treebear.kwifimanager.util.Check;

public class MyNodePresenter extends BasePresenter<MyNodeContract.View, MyNodeContract.Model> implements MyNodeContract.Presenter {
    @Override
    public void setModel() {
        mModel = new MyNodeModel();
    }

    @Override
    public void getNodeList(int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.PAGE_SIZE);
        mModel.getNodeList(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<NodeInfoDetail>>() {
            @Override
            public void onSuccess(BaseResponse<NodeInfoDetail> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void modifyNodeName(String nodeId, String name) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.NAME, name);
        mModel.modifyNodeName(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.modifyNodeNameResponse(ApiCode.SUCC, "修改成功");
                }
            }
        });
    }

    @Override
    public void unbindNode(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.unbindNode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.unbindNodeResponse(ApiCode.SUCC, "");
                }
            }
        });
    }

    @Override
    public void upgradeNode(String nodeId) {

    }
}
