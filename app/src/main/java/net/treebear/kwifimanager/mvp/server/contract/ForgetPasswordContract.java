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

    interface IForgetPasswordView extends IView<Object> {

    }

    interface IForgetPasswordPresenter extends IPresenter<IForgetPasswordView> {
        void setPassword(String mobile, String vcode, String passwd);
    }

    interface IForgetPasswordModel extends IModel {

        void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }
}
