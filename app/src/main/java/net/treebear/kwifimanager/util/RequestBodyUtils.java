package net.treebear.kwifimanager.util;

import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyUtils {
    private RequestBodyUtils() {
    }

    /**
     * 将Json转换为RequestBody
     *
     * @param object JSON对象
     * @return RequestBody 请求体
     */
    @SuppressWarnings("unused")
    public static RequestBody convert(JSONObject object) {
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
    public static RequestBody convert(ArrayMap<String, Object> params) {
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
        return convert(jsonObject);
    }

    /**
     * ArrayMap to JSONObject
     *
     * @param params 承载参数的map
     * @return 承载参数的JSONObject
     */
    public static JSONObject map2JsonObject(ArrayMap<String, Object> params) {
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

}
