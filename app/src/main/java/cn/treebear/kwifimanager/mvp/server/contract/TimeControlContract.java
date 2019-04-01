package cn.treebear.kwifimanager.mvp.server.contract;

import android.support.annotation.Size;

import java.util.List;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.TimeControlbean;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface TimeControlContract {

    interface View extends IView<TimeControlbean> {

        void onSetAllowTimeResponse(BaseResponse response);

        void onDeleteAllowTimeResponse(BaseResponse response);
    }

    interface Presenter extends IPresenter<View> {

        void getTimeControlPlan(String nodeId);

        void setTimeControlPlan(@Size(min = 1) String nodeId, long id, @Size(min = 1) String name, String startTime, String endTime, int repeat, List<String> mac);

        void deleteTimeControlPlan(@Size(min = 1) String nodeId, long id);
    }

    interface Model extends IModel {

        void getTimeControlPlan(RequestBody body, AsyncCallBack<BaseResponse<TimeControlbean>> callBack);

        void setTimeControlPlan(RequestBody body, AsyncCallBack<BaseResponse<Object>> callBack);

        void deleteTimeControlPlan(RequestBody body, AsyncCallBack<BaseResponse<Object>> callBack);
    }


}
