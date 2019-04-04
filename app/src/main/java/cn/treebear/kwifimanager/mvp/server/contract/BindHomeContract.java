package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface BindHomeContract {

    interface View extends IView<NodeInfoDetail> {

        void onMessageListResponse(MessageInfoBean data);

        void onMessageListError(BaseResponse error);

        void onMobileListResponse(MobileListBean data);

        void onModifyMobileInfoResponse(BaseResponse response);

        void onMobileListError(BaseResponse error);

        void onChildrenListResponse(ChildrenListBean data);

        void onChildrenListError(BaseResponse error);
    }

    interface Presenter extends IPresenter<View> {

        void getNodeList();

        void getMessageList(int pageNo);

        void setNodeMobileInfo(String nodeId, String mac, String note, int block);

        void getMobileList(String nodeId, int pageNo);

        void getChildrenList(String nodeId, int pageNo);
    }

    interface Model extends IModel {
        void getNodeList(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack);

        void getMessageList(RequestBody params, AsyncCallBack<BaseResponse<MessageInfoBean>> callBack);

        void setMobileInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void getMobileList(RequestBody params, AsyncCallBack<BaseResponse<MobileListBean>> callBack);

        void getChildrenList(RequestBody params, AsyncCallBack<BaseResponse<ChildrenListBean>> callBack);
    }


}
