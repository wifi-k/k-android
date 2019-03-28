package cn.treebear.kwifimanager.mvp.server.contract;

import java.util.ArrayList;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.HealthyModelBean;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
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
