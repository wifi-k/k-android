package cn.treebear.kwifimanager.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;

import com.nanchen.compresshelper.CompressHelper;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;

import cn.treebear.kwifimanager.R;

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
        UMImage umImage = new UMImage(activity, new File(imgRes));
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
     * @param imgRes      图 片 资 源
     * @param listener    分 享 监 听
     */
    public static void shareWxImage(Activity activity, String title, String description, String imgRes, UMShareListener listener) {
        if (imgRes == null) {
            return;
        }
        if (imgRes.endsWith(".gif") || imgRes.endsWith(".GIF")) {
            shareWxGif(activity, imgRes, title, description);
        } else {
            UMImage umImage = new UMImage(activity, new File(imgRes));
            umImage.compressStyle = UMImage.CompressStyle.QUALITY;
            umImage.setThumb(new UMImage(activity, compressImg(activity, imgRes)));
            umImage.setTitle(title);
            new ShareAction(activity)
                    .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                    .setCallback(listener == null ? defaultShareListener : listener)//回调监听器
                    .withMedia(umImage)
                    .withText(description)
                    .share();
        }
    }

    public static void shareWxGif(Activity activity, String imgRes, String title, String description) {
        UMEmoji emoji = new UMEmoji(activity, new File(imgRes));
        emoji.setThumb(new UMImage(activity, R.mipmap.ic_launcher));
        emoji.setTitle(title);
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .setCallback(defaultShareListener)//回调监听器
                .withMedia(emoji)
                .withText(description)
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

    public static File compressImg(Context ctx, String path) {
        return new CompressHelper.Builder(ctx)
                .setMaxWidth(360)  // 默认最大宽度为720
                .setMaxHeight(480) // 默认最大高度为960
                .setQuality(60)    // 默认压缩质量为80
                .setFileName("temp" + FileUtils.createNewFileName()) // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(FileUtils.getPublicDiskSafe())
                .build()
                .compressToFile(new File(path));
    }

}
