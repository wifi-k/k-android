package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.mvp.server.contract.ChildrenListContract;
import okhttp3.RequestBody;

public class ChildrenListModel extends BaseServerModel implements ChildrenListContract.Model {
    @Override
    public void getChildrenList(RequestBody body, AsyncCallBack<BaseResponse<ChildrenListBean>> callBack) {
        bindObservable(mService.getChildrenList(body), callBack);
    }
}
