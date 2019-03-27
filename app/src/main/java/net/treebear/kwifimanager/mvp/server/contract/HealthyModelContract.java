package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.HealthyModelBean;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import java.util.ArrayList;

import okhttp3.RequestBody;

public interface HealthyModelContract {

    interface View extends IView<HealthyModelBean> {
        void onSetInfoSuccess();

        void onSetInfoFailed(BaseResponse response);
    }

    interface Presenter extends IPresenter<View> {
        void getHealthyModelInfo(String nodeId);

        void setHealthyModelInfo(String nodeId, int op, ArrayList<HealthyModelBean.WifiBean> bean);
    }

    interface Model extends IModel {

        void getHealthyModelInfo(RequestBody params, AsyncCallBack<BaseResponse<HealthyModelBean>> callBack);

        void setHealthyModelInfo(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }
}
