package net.treebear.kwifimanager.http;


import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.AdvertisementBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author tinlone
 * @date 2017/6/6 0006
 */

public interface HttpService {


    /**
     * 手机APP开屏页
     * from 	string 	是 	（安卓：android；水果：iOS）
     * type 	string 	是
     */
    @GET("openscreen/pic")
    Observable<BaseResponse<AdvertisementBean>> getAdvertisement(@QueryMap() Map<String, Object> params);

}
