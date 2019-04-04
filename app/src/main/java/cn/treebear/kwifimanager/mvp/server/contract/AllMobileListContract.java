package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface AllMobileListContract {

    interface View extends IView<MobileListBean> {

        void onModifyMobileInfoResponse(BaseResponse response);

    }

    interface Presenter extends IPresenter<View> {

        void setNodeMobileInfo(String nodeId, String mac, String note, int block, int isRecord, int alarm);

        void getMobileList(String nodeId, int pageNo, int pageSize);
    }

    interface Model extends IModel {

        void setMobileInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void getMobileList(RequestBody params, AsyncCallBack<BaseResponse<MobileListBean>> callBack);
    }


}
