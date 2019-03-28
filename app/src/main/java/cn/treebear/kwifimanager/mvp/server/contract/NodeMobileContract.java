package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface NodeMobileContract {

    interface View extends IView<MobileListBean> {

        void setNodeMobileSuccess();

        void setNodeMobileFail();
    }

    interface Presenter extends IPresenter<View> {

        void getNodeMobileList(String nodeId, int pageNo);

        void setNodeMobileInfo(String nodeId,String mac,String note, int block);

    }

    interface Model extends IModel {

        void getNodeMobileList(RequestBody params, AsyncCallBack<BaseResponse<MobileListBean>> callBack);

        void setNodeMobileInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

    }

}
