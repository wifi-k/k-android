package cn.treebear.kwifimanager.mvp.server.contract;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import okhttp3.RequestBody;

public interface SelectXiaoKContract {

    interface View extends IView<NodeInfoDetail> {

        void onSelectXiaoK(BaseResponse response);

    }

    interface Presenter extends IPresenter<View> {
        void getXiaoKList(int pageNo);

        void selectXiaoK(String nodeId);

    }

    interface Model extends IModel {

        void getNodeListAll(RequestBody params, AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack);

        void selectXiaoK(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);

    }

}
