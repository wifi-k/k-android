package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.NodeOptionSetContract;
import net.treebear.kwifimanager.mvp.server.model.NodeOptionSetModel;

public class NodeOptionSetPresenter extends BasePresenter<NodeOptionSetContract.View, NodeOptionSetContract.Model> implements NodeOptionSetContract.Presenter {
    @Override
    public void setModel() {
        mModel = new NodeOptionSetModel();
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
