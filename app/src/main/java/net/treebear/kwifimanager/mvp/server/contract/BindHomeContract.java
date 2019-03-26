package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

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
