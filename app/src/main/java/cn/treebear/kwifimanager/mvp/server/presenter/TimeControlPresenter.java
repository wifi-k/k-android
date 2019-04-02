package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.TimeControlbean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.contract.TimeControlContract;
import cn.treebear.kwifimanager.mvp.server.model.TimeCotrolModel;

public class TimeControlPresenter extends BasePresenter<TimeControlContract.View, TimeControlContract.Model> implements TimeControlContract.Presenter {

    private Gson gson;

    @Override
    public void setModel() {
        mModel = new TimeCotrolModel();
        gson = new GsonBuilder().create();
    }

    @Override
    public void getTimeControlPlan(String nodeId) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        mModel.getTimeControlPlan(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<TimeControlbean>>() {
            @Override
            public void onSuccess(BaseResponse<TimeControlbean> resultData) {
                if (mView != null) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void setTimeControlPlan(String nodeId, long id, String name, String startTime, String endTime, int repeat, List<String> mac) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.ID, id);
        map.put(Keys.NAME, name);
        map.put(Keys.START_TIME, startTime);
        map.put(Keys.END_TIME, endTime);
        map.put(Keys.WHICH_TIME, repeat);
        map.put(Keys.MAC, gson.toJson(mac));
        mModel.setTimeControlPlan(convertRequestBody(map), new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onSetAllowTimeResponse(resultData);
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onSetAllowTimeResponse(resultData);
                }
            }
        });

    }

    @Override
    public void deleteTimeControlPlan(String nodeId, long id) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.ID, id);
        mModel.deleteTimeControlPlan(convertRequestBody(map), new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onDeleteAllowTimeResponse(resultData);
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onDeleteAllowTimeResponse(resultData);
                }
            }
        });
    }
}
