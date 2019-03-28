package cn.treebear.kwifimanager.mvp.server.presenter;

import android.util.ArrayMap;

import cn.treebear.kwifimanager.base.BasePresenter;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserMobileContract;
import cn.treebear.kwifimanager.mvp.server.model.ModifyUserMobileModel;
import cn.treebear.kwifimanager.util.Check;

public class ModifyUserMobilePresenter extends BasePresenter<ModifyUserMobileContract.View, ModifyUserMobileContract.Model> implements ModifyUserMobileContract.Presenter {
    @Override
    public void setModel() {
        mModel = new ModifyUserMobileModel();
    }

    @Override
    public void getVerifyCode(String mobile) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.TYPE, Config.RequestType.VERIFY_CODE_MODIFY_PHONE);
        mModel.getVerifyCode(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> resultData) {
                if (Check.hasContent(resultData, mView)) {
                    mView.onLoadData(resultData.getData());
                }
            }
        });
    }

    @Override
    public void modifyMobileByVerify(String mobile, String code) {
        ArrayMap<String, Object> map = map();
        map.put(Keys.MOBILE, mobile);
        map.put(Keys.VERIFY_CODE, code);
        mModel.modifyMobileByVerify(convertRequestBody(map), new BaseAsyncCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                if (mView != null) {
                    mView.onMobileModifySuccess();
                }
            }

            @Override
            public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                if (mView != null) {
                    mView.onModifyFailed();
                }
            }
        });
    }
}
