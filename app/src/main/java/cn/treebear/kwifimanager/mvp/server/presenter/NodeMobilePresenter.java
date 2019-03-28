package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.contract.NodeMobileContract;
import cn.treebear.kwifimanager.mvp.server.model.NodeMobileModel;
import cn.treebear.kwifimanager.util.Check;

public class NodeMobilePresenter extends BasePresenter<NodeMobileContract.View, NodeMobileContract.Model> implements NodeMobileContract.Presenter {
    @Override
    public void setModel() {
        mModel = new NodeMobileModel();
    }

    @Override
    public void getNodeMobileList(String nodeId, int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.PAGE_SIZE);
        mModel.getNodeMobileList(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<MobileListBean>>() {
            @Override
            public void onSuccess(BaseResponse<MobileListBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void setNodeMobileInfo(String nodeId, String mac, String note, int block) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.MAC, mac);
        map.put(Keys.NOTE, note);
        map.put(Keys.BLOCK, block);
        mModel.setNodeMobileInfo(convertRequestBody(map), new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.setNodeMobileSuccess();
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.setNodeMobileFail();
                }
            }
        });
    }
}
