package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface NodeOptionSetContract {

    interface View extends IView<BaseResponse> {
        void onSSIDResponseOK();

        void onPwdResponseOK();

    }

    interface Presenter extends IPresenter<View> {
        void modifySsid(String nodeId, int freq, String ssid);

        void modifyPasswd(String nodeId, int freq, String passwd);
    }

    interface Model extends IModel {
        void modifySsid(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

        void modifyPasswd(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

    }
}
