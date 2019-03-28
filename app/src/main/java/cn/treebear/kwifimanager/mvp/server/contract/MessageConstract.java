package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
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
