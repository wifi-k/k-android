package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.HealthyModelBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.contract.HealthyModelContract;
import cn.treebear.kwifimanager.mvp.server.model.HealthyMModel;
import cn.treebear.kwifimanager.util.Check;

public class HealthyModelPresenter extends BasePresenter<HealthyModelContract.View, HealthyModelContract.Model> implements HealthyModelContract.Presenter {
    @Override
    public void setModel() {
        mModel = new HealthyMModel();
    }

    @Override
    public void getHealthyModelInfo(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.getHealthyModelInfo(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<HealthyModelBean>>() {
            @Override
            public void onSuccess(BaseResponse<HealthyModelBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void setHealthyModelInfo(String nodeId, int op, ArrayList<HealthyModelBean.WifiBean> bean) {
        JSONObject params = new JSONObject();
        try {
            params.put(Keys.NODE_ID, nodeId);
            params.put(Keys.OP, op);
            params.put(Keys.WIFI, new GsonBuilder().create().toJson(bean));
            mModel.setHealthyModelInfo(convertRequestBody(params), new IModel.AsyncCallBack<BaseResponse<Object>>() {
                @Override
                public void onSuccess(BaseResponse<Object> resultData) {
                    if (mView != null) {
                        mView.onSetInfoSuccess();
                    }
                }

                @Override
                public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                    if (mView != null) {
                        mView.onSetInfoFailed(resultData);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
