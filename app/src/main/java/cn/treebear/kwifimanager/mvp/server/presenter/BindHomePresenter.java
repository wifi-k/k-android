package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.IModel;
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
        map.put(Keys.PAGE_SIZE, 10);
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
    public void getMessageList(int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.HOME_NOTICE_PAGE_SIZE);
        mModel.getMessageList(convertRequestBody(map), new IModel.AsyncCallBack<BaseResponse<MessageInfoBean>>() {
            @Override
            public void onSuccess(BaseResponse<MessageInfoBean> resultData) {
                if (mView != null) {
                    mView.onMessageListResponse(resultData.getData());
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onMessageListError(resultData);
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
    public void getMobileList(String nodeId, int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.HOME_MOBILE_PAGE_SIZE);
        mModel.getMobileList(convertRequestBody(map), new IModel.AsyncCallBack<BaseResponse<MobileListBean>>() {
            @Override
            public void onSuccess(BaseResponse<MobileListBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onMobileListResponse(resultData.getData());
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onMobileListError(resultData);
                }
            }
        });
    }

    @Override
    public void getChildrenList(String nodeId, int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NODE_ID, nodeId);
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.HOME_MOBILE_PAGE_SIZE);
        mModel.getChildrenList(convertRequestBody(map), new IModel.AsyncCallBack<BaseResponse<ChildrenListBean>>() {
            @Override
            public void onSuccess(BaseResponse<ChildrenListBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onChildrenListResponse(resultData.getData());
                }
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onChildrenListError(resultData);
                }
            }
        });
    }
}
