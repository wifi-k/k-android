package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface BindHomeContract {

    interface View extends IView<NodeInfoDetail> {

    }

    interface Presenter extends IPresenter<View> {

        void getNodeList();

    }

    interface Model extends IModel {
        void getNodeList(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack);
    }


}
