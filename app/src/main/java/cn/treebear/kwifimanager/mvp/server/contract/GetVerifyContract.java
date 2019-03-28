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
public interface GetVerifyContract {

    interface View extends IView<String> {

    }

    interface Presenter extends IPresenter<View> {

        void getGetVerifyCode(int type, String mobile);
    }

    interface Model extends IModel {

        void getGetVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack);

    }
}
