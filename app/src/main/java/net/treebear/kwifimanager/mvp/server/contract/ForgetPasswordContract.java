package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */
public interface ForgetPasswordContract {

    interface View extends IView<Object> {

    }

    interface Presenter extends IPresenter<View> {
        void setPassword(String mobile, String vcode, String passwd);
    }

    interface Model extends IModel {

        void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }
}
