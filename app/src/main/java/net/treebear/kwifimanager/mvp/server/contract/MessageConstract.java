package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.MessageInfoBean;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface MessageConstract {

    interface IMessageView extends IView<MessageInfoBean> {
    }

    interface IMessagePresenter extends IPresenter<IMessageView> {

        void getMessageInfoList(int pageNo);
    }

    interface IMessageModel extends IModel {
        void getMessageInfoList(RequestBody params, AsyncCallBack<BaseResponse<MessageInfoBean>> callBack);
    }
}
