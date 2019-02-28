package net.treebear.kwifimanager.base;

import android.util.ArrayMap;

import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;
import net.treebear.kwifimanager.util.TLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {

    protected M mModel = null;
    protected V mView = null;
    protected JSONObject EMPTY_BODY = new JSONObject();
    private ArrayMap<String, Object> EMPTY_MAP;

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
        if (EMPTY_MAP == null) {
            EMPTY_MAP = new ArrayMap<>(16);
        } else {
            EMPTY_MAP.clear();
        }
        return EMPTY_MAP;
    }

    /**
     * 将Json转换为RequestBody
     *
     * @param object JSON对象
     * @return RequestBody 请求体
     */
    @SuppressWarnings("unused")
    protected RequestBody convertRequestBody(JSONObject object) {
        if (object == null) {
            return null;
        }
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), object.toString());
    }

    /**
     * 将map对象转换为RequestBody
     *
     * @param params 承载参数的map
     * @return RequestBody 请求体
     */
    protected RequestBody convertRequestBody(ArrayMap<String, Object> params) {
        if (params == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return convertRequestBody(jsonObject);
    }

    /**
     * ArrayMap to JSONObject
     * @param params 承载参数的map
     * @return 承载参数的JSONObject
     */
    protected JSONObject convertJsonObject(ArrayMap<String, Object> params) {
        if (params == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
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
     * @param <Data> data数据
     */
    protected abstract class BaseAsyncCallback<Data> implements IModel.AsyncCallBack<Data> {

        @Override
        public abstract void onSuccess(Data resultData);

        @Override
        public void onFailed(String resultMsg, int resultCode) {
            if (mView != null) {
                mView.onLoadFail(resultMsg, resultCode);
            }
        }
    }
}
