package net.treebear.kwifimanager.base;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;
import net.treebear.kwifimanager.util.TLog;
import net.treebear.kwifimanager.util.UserInfoUtil;

import java.util.HashMap;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {

    protected M mModel = null;
    protected V mView = null;
    private HashMap<String, Object> baseParams;

    @Override
    public void attachView(V view) {
        mView = view;
        setModel();
    }

    protected HashMap<String, Object> getBaseParams() {
        if (baseParams == null) {
            baseParams = new HashMap<>(16);
        } else {
            baseParams.clear();
        }
        if (UserInfoUtil.isLogin()) {
            baseParams.put("token", UserInfoUtil.getUserInfo().getToken());
            baseParams.put("userId", UserInfoUtil.getUserInfo().getUserId());
        }
        baseParams.put("from", "android");
        baseParams.put("version", BuildConfig.VERSION_CODE);
        return baseParams;
    }

    public abstract void setModel();

    @Override
    public void dettachView() {
        if (mModel != null) {
            mModel.cancelRequest();
            mModel = null;
        }
        mView = null;
        TLog.i("(BasePresenter.java:31) -> dettachView");
    }

    protected abstract class BaseAsyncCallback<Data> implements IModel.AsyncCallBack<Data> {

        @Override
        public abstract void onSuccess(Data resultData);

        @Override
        public void onFailed(String resultMsg, String resultCode) {
            if (mView != null) {
                mView.onLoadFail(resultMsg, resultCode);
            }
        }
    }
}
