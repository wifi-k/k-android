package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import cn.treebear.kwifimanager.mvp.server.model.AllMobileListModel;
import cn.treebear.kwifimanager.util.Check;

public class AllMobileListPresenter extends BasePresenter<AllMobileListContract.View, AllMobileListContract.Model> implements AllMobileListContract.Presenter {
    @Override
    public void setModel() {
        mModel = new AllMobileListModel();
    }

    @Override
    public void setNodeMobileInfo(String nodeId, String mac, String note, int block, int isRecord, int alarm) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.MAC, mac);
        map.put(Keys.NOTE, note);
        map.put(Keys.BLOCK, block);
        map.put(Keys.IS_RECORD,isRecord);
        map.put(Keys.IS_ONLINE_ALARM,alarm);
        mModel.setMobileInfo(convertRequestBody(map), new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onModifyMobileInfoResponse(resultData);
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onModifyMobileInfoResponse(resultData);
                }
            }
        });
    }

    @Override
    public void getMobileList(String nodeId, int pageNo, int pageSize) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, pageSize);
        mModel.getMobileList(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<MobileListBean>>() {
            @Override
            public void onSuccess(BaseResponse<MobileListBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}
