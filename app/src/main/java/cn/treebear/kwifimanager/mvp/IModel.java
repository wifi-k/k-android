package cn.treebear.kwifimanager.mvp;

import cn.treebear.kwifimanager.base.BaseResponse;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public interface IModel {

    /**
     * 取消请求
     */
    void cancelRequest();

    interface AsyncCallBack<Data> {

        /**
         * 成功
         *
         * @param resultData 数据
         */
        void onSuccess(Data resultData);

        /**
         * 失败
         *
         * @param resultMsg  信息
         * @param resultCode 错误码
         */
        void onFailed(BaseResponse resultData, String resultMsg, int resultCode);

    }
}