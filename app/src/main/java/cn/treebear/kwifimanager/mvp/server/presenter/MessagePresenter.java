package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.MessageConstract;
import cn.treebear.kwifimanager.mvp.server.model.MessageModel;

public class MessagePresenter extends BasePresenter<MessageConstract.View, MessageConstract.Model> implements MessageConstract.Presenter {
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
