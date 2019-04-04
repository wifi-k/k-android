package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface ChildrenListContract {

    interface View extends IView<ChildrenListBean> {

    }

    interface Presenter extends IPresenter<View> {
        void getChildrenList(String nodeId, int pageNo);
    }

    interface Model extends IModel {

        void getChildrenList(RequestBody body, AsyncCallBack<BaseResponse<ChildrenListBean>> callBack);
    }

}
