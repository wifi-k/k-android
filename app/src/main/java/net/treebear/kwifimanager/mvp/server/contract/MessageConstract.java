package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.MessageInfoBean;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface MessageConstract {

    interface View extends IView<MessageInfoBean> {
    }

    interface Presenter extends IPresenter<View> {

        void getMessageInfoList(int pageNo);
    }

    interface Model extends IModel {
        void getMessageInfoList(RequestBody params, AsyncCallBack<BaseResponse<MessageInfoBean>> callBack);
    }
}
