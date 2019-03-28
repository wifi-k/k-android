package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */
public interface ForgetPasswordContract {

    interface View extends IView<BaseResponse> {

    }

    interface Presenter extends IPresenter<View> {
        void setPassword(String mobile, String vcode, String passwd);
    }

    interface Model extends IModel {

        void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }
}