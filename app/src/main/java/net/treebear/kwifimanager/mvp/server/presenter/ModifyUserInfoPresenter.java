package net.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import net.treebear.kwifimanager.base.BasePresenter;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.QiNiuUserBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.ModifyUserInfoContract;
import net.treebear.kwifimanager.mvp.server.model.ModifyUserInfoModel;
import net.treebear.kwifimanager.util.Check;

public class ModifyUserInfoPresenter extends BasePresenter<ModifyUserInfoContract.IUserInfoView, ModifyUserInfoContract.IUserInfoModel> implements ModifyUserInfoContract.IUserInfoPresenter {
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
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void setUserAvatar(String name, String picUrl) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.NAME, name);
        map.put(Keys.AVATAR, picUrl);
        mModel.setUserAvatar(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView!=null){
                    mView.onUserAvatarUpload();
                }
            }
        });
    }
}
