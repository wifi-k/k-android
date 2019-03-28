package cn.treebear.kwifimanager.util;

import android.app.Activity;
import android.support.annotation.DrawableRes;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class UMShareUtils {

    private static UMShareListener defaultShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            TLog.e(throwable);
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    /**
     * 面板分享
     *
     * @param activity    content 对象
     * @param title       标 题
     * @param description 描 述
     * @param url         分 享 路 径
     * @param imgRes      图 片 资 源
     * @param listener    分 享 监 听
     */
    public static void shareWxWithPanelImage(Activity activity, String title, String description, String url, String imgRes, UMShareListener listener) {
        UMImage umImage = new UMImage(activity, imgRes);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN)
                .setCallback(listener == null ? defaultShareListener : listener)
                .withMedia(web)
                .open();
    }

    /**
     * 面板分享
     *
     * @param activity    content 对象
     * @param title       标 题
     * @param description 描 述
     * @param url         分 享 路 径
     * @param imgRes      图 片 资 源
     * @param listener    分 享 监 听
     */
    public static void shareWxWithPanelImage(Activity activity, String title, String description, String url, @DrawableRes int imgRes, UMShareListener listener) {
        UMImage umImage = new UMImage(activity, imgRes);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN)
                .setCallback(listener == null ? defaultShareListener : listener)
                .withMedia(web)
                .open();
    }

    /**
     * 无面板分享
     *
     * @param activity    content 对象
     * @param title       标 题
     * @param description 描 述
     * @param url         分 享 路 径
     * @param imgRes      图 片 资 源
     * @param listener    分 享 监 听
     */
    public static void shareWxLink(Activity activity, String title, String description, String url, String imgRes, UMShareListener listener) {
        UMImage umImage = new UMImage(activity, imgRes);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .setCallback(listener == null ? defaultShareListener : listener)//回调监听器
                .withMedia(web)
                .share();
    }

    /**
     * 无面板分享
     *
     * @param activity    content 对象
     * @param title       标 题
     * @param description 描 述
     * @param url         分 享 路 径
     * @param imgRes      图 片 资 源
     * @param listener    分 享 监 听
     */
    public static void shareWxLink(Activity activity, String title, String description, String url, @DrawableRes int imgRes, UMShareListener listener) {
        UMImage umImage = new UMImage(activity, imgRes);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .setCallback(listener == null ? defaultShareListener : listener)//回调监听器
                .withMedia(web)
                .share();
    }

}
