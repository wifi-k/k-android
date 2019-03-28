package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.NodeWifiListBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.NodeOptionSetContract;
import cn.treebear.kwifimanager.mvp.server.model.NodeOptionSetModel;
import cn.treebear.kwifimanager.util.Check;

public class NodeOptionSetPresenter extends BasePresenter<NodeOptionSetContract.View, NodeOptionSetContract.Model> implements NodeOptionSetContract.Presenter {
    @Override
    public void setModel() {
        mModel = new NodeOptionSetModel();
    }

    @Override
    public void getNodeSsid(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.getNodeSsid(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<NodeWifiListBean>>() {
            @Override
            public void onSuccess(BaseResponse<NodeWifiListBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void modifySsid(String nodeId, int freq, String ssid) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.FREQ, freq);
        map.put(Keys.SSID, ssid);
        mModel.modifySsid(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onSSIDResponseOK();
                }
            }
        });
    }

    @Override
    public void modifyPasswd(String nodeId, int freq, String passwd) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.FREQ, freq);
        map.put(Keys.PASSWD_WIFI, passwd);
        mModel.modifyPasswd(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onPwdResponseOK();
                }
            }
        });

    }
}
