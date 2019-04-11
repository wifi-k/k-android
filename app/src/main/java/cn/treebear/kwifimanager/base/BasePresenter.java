package cn.treebear.kwifimanager.base;

import android.util.ArrayMap;

import org.json.JSONObject;

import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.IView;
import cn.treebear.kwifimanager.util.RequestBodyUtils;
import cn.treebear.kwifimanager.util.TLog;
import okhttp3.RequestBody;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {

    protected M mModel = null;
    protected V mView = null;
    protected JSONObject emptyBody = new JSONObject();
    private ArrayMap<String, Object> emptyMap;

    @Override
    public void attachView(V view) {
        mView = view;
        setModel();
    }

    /**
     * 获取空map对象
     *
     * @return ArrayMap 用于承载参数的map
     */
    protected ArrayMap<String, Object> map() {
        if (emptyMap == null) {
            emptyMap = new ArrayMap<>(16);
        } else {
            emptyMap.clear();
        }
//        FormBody body=new FormBody.Builder()
//                .add("username","admin")
//                .add("token","sjdkdjows=dmzkkshf")
//                .build();
        return emptyMap;
    }

    /**
     * 将Json转换为RequestBody
     *
     * @param object JSON对象
     * @return RequestBody 请求体
     */
    @SuppressWarnings("unused")
    protected RequestBody convertRequestBody(JSONObject object) {
        return RequestBodyUtils.convert(object);
    }

    /**
     * 将map对象转换为RequestBody
     *
     * @param params 承载参数的map
     * @return RequestBody 请求体
     */
    protected RequestBody convertRequestBody(ArrayMap<String, Object> params) {
        return RequestBodyUtils.convert(params);
    }

    /**
     * ArrayMap to JSONObject
     *
     * @param params 承载参数的map
     * @return 承载参数的JSONObject
     */
    protected JSONObject convertJsonObject(ArrayMap<String, Object> params) {
        return RequestBodyUtils.map2JsonObject(params);
    }

    /**
     * 配置model对象
     */
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

    /**
     * 统一处理失败请求的基类回调
     *
     * @param <Data> data数据
     */
    protected abstract class BaseAsyncCallback<Data> implements IModel.AsyncCallBack<Data> {

        @Override
        public abstract void onSuccess(Data resultData);

        @Override
        public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
            if (mView != null) {
                mView.onLoadFail(resultData, resultMsg, resultCode);
            }
        }
    }
}
