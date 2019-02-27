package net.treebear.kwifimanager.mvp.contract;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import okhttp3.RequestBody;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */
public interface SetPasswordContract {

    interface ISetPasswordView extends IView<Object> {

    }

    interface ISetPasswordPresenter extends IPresenter<ISetPasswordView> {
        /**
         * 获取启动页广告
         */
        void setPassword(String passwd);
    }

    interface ISetPasswordModel extends IModel {

        /**
         * 获取广告
         *
         * @param params   参数
         * @param callBack 回调
         */
        void setPassword(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack);
    }
}
