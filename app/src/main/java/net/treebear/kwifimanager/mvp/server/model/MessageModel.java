package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.bean.MessageInfoBean;
import net.treebear.kwifimanager.mvp.server.contract.MessageConstract;

import okhttp3.RequestBody;

public class MessageModel extends BaseServerModel implements MessageConstract.Model {
    @Override
    public void getMessageInfoList(RequestBody params, AsyncCallBack<BaseResponse<MessageInfoBean>> callBack) {
        bindObservable(mService.getMessageList(params),callBack);
    }
}
