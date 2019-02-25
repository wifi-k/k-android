package net.treebear.kwifimanager.mvp.contract;

import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.IView;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.AdvertisementBean;

import java.util.HashMap;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */
public interface AdvertisementContract {

    interface IAdvertisementView extends IView<AdvertisementBean> {

    }

    interface IAdvertisementPresenter extends IPresenter<IAdvertisementView> {
        /**
         * 获取启动页广告
         */
        void getAdvertisement();
    }

    interface IAdvertisementModel extends IModel {

        /**
         * 获取广告
         *
         * @param params   参数
         * @param callBack 回调
         */
        void getAdvertisement(HashMap<String, Object> params, AsyncCallBack<BaseResponse<AdvertisementBean>> callBack);
    }
}
