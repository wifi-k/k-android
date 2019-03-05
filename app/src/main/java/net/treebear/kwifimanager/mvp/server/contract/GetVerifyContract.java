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
public interface GetVerifyContract {

    interface IGetVerifyView extends IView<String> {

    }

    interface IGetVerifyPresenter extends IPresenter<IGetVerifyView> {

        void getGetVerifyCode(int type, String mobile);
    }

    interface IGetVerifyModel extends IModel {

        void getGetVerifyCode(RequestBody params, AsyncCallBack<BaseResponse<String>> callBack);

    }
}
