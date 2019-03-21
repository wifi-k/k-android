package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.MessageInfoBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.MessageConstract;
import net.treebear.kwifimanager.mvp.server.model.MessageModel;

public class MessagePresenter extends BasePresenter<MessageConstract.IMessageView, MessageConstract.IMessageModel> implements MessageConstract.IMessagePresenter {
    @Override
    public void setModel() {
        mModel = new MessageModel();
    }

    @Override
    public void getMessageInfoList(int pageNo) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.PAGE_NO, pageNo);
        map.put(Keys.PAGE_SIZE, Config.Numbers.PAGE_SIZE);
        mModel.getMessageInfoList(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<MessageInfoBean>>() {
            @Override
            public void onSuccess(BaseResponse<MessageInfoBean> resultData) {
                if (mView != null) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }
}
