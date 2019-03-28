package cn.treebear.kwifimanager.mvp.server.model;

import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseServerModel;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.mvp.server.contract.MessageConstract;
import okhttp3.RequestBody;

public class MessageModel extends BaseServerModel implements MessageConstract.Model {
    @Override
    public void getMessageInfoList(RequestBody params, AsyncCallBack<BaseResponse<MessageInfoBean>> callBack) {
        bindObservable(mService.getMessageList(params), callBack);
    }
}
