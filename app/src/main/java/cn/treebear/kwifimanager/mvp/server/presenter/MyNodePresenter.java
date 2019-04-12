package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.mvp.server.contract.MyNodeContract;
import cn.treebear.kwifimanager.mvp.server.model.MyNodeModel;
import cn.treebear.kwifimanager.util.Check;

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

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.unbindNodeResponse(resultCode, resultMsg);
                }
            }
        });
    }

    @Override
    public void upgradeNode(String nodeId) {

    }
}
