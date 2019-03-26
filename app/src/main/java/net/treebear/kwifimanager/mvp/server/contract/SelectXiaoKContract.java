package net.treebear.kwifimanager.mvp.server.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

public interface SelectXiaoKContract {

    interface View extends IView<NodeInfoDetail>{

        void onSelectXiaoK(BaseResponse response);

    }

    interface Presenter extends IPresenter<View>{
        void getXiaoKList(int pageNo);

        void selectXiaoK(String nodeId);

    }

    interface Model extends IModel{

        void getNodeListAll(RequestBody params,AsyncCallBack<BaseResponse<NodeInfoDetail>> callBack);

        void selectXiaoK(RequestBody params,AsyncCallBack<BaseResponse<Object>> callBack);

    }

}
