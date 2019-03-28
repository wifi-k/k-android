package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.QiNiuUserBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserInfoContract;
import cn.treebear.kwifimanager.mvp.server.model.ModifyUserInfoModel;
import cn.treebear.kwifimanager.util.Check;

public class ModifyUserInfoPresenter extends BasePresenter<ModifyUserInfoContract.View, ModifyUserInfoContract.Model> implements ModifyUserInfoContract.Presenter {
    @Override
    public void setModel() {
        mModel = new ModifyUserInfoModel();
    }

    @Override
    public void getQiNiuToken() {
        mModel.getQiNiuToken(new BaseAsyncCallback<BaseResponse<QiNiuUserBean>>() {
            @Override
            public void onSuccess(BaseResponse<QiNiuUserBean> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onQiNiuTokenResponse(resultData.getData());
                }
            }
        });
    }

    @Override
    public void modifyUserInfo(String name, String picUrl) {
        ArrayMap<String, Object> map = map();
        if (Check.hasContent(name)) {
            map.put(Keys.NAME, name);
        }
        if (Check.hasContent(picUrl)) {
            map.put(Keys.AVATAR, picUrl);
        }
        mModel.setUserAvatar(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onModifyUserInfo();
                }
            }
        });
    }
}
