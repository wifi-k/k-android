package net.treebear.kwifimanager.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.receiver.OpenFileReceiver;
import net.treebear.kwifimanager.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Tinlone
 * 下载服务，包含通知(适配Android7+)
 * 需要文件系统权限{@link Manifest.permission_group#STORAGE}，
 * 请将网络权限{@link Manifest.permission#INTERNET}
 * 弹窗权限{@link Manifest.permission#ACCESS_NOTIFICATION_POLICY}加入清单文件
 * 若适用于安装文件，还需{@link Manifest.permission#REQUEST_INSTALL_PACKAGES}
 */
public class ODownloadService extends Service {
    private NotificationManager notificationManager;
    private Notification.Builder builder;
    private OkHttpClient okHttpClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        okHttpClient = new OkHttpClient();
        return new DownloadBinder();
    }

    //初始化通知
    private void initNotification() {
        notificationManager = (NotificationManager) MyApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(MyApplication.getAppContext());
        if (Build.VERSION.SDK_INT >= 26 && notificationManager != null) {
            //第三个参数设置通知的优先级别
            NotificationChannel channel =
                    new NotificationChannel(BuildConfig.APPLICATION_ID, "下载及更新", NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//是否可以绕过请勿打扰模式
            channel.canShowBadge();//是否可以显示icon角标
            channel.enableLights(true);//是否显示通知闪灯
            channel.setBypassDnd(true);//设置绕过免打扰
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_SECRET);
            channel.setLightColor(Color.GREEN);//设置闪光灯颜色
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(BuildConfig.APPLICATION_ID);//这个id参数要与上面channel构建的第一个参数对应
        }
        builder.setContentTitle("正在下载...") //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                .setContentText("下载进度:" + "0%")
                .setProgress(100, 0, false);
        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        initNotification();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed(call, e);
                builder.setContentTitle("下载失败")
                        .setContentText("请稍后再试")
                        .setAutoCancel(true);//设置通知被点击一次是否自动取消
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                // buffer写入大小
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = FileUtils.createDirInStorage(saveDir);
                try {
                    ResponseBody body = response.body();
                    if (body == null) {
                        return;
                    }
                    //取字节流
                    is = body.byteStream();
                    //获取流大小
                    long total = body.contentLength();
                    //准备文件对象
                    File file = new File(savePath, getNameFromUrl(url));
                    //准备文件流
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    //循环读文件，直到流结束
                    while ((len = is.read(buf)) != -1) {
                        //写入文件流
                        fos.write(buf, 0, len);
                        sum += len;
                        //计算进度
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 回调下载中
                        listener.onDownloading(progress);
                        //更新进度
                        builder.setContentText(String.format("下载进度:%s%%", progress))
                                .setProgress(100, progress, true);
                        notificationManager.notify(1, builder.build());
                    }
                    //刷入文件流，防止文件写入不完整
                    fos.flush();
                    // 处理通知栏信息
                    Intent clickIntent = new Intent(MyApplication.getAppContext(), OpenFileReceiver.class);
                    clickIntent.putExtra("path", file.getAbsolutePath());
                    //点击通知之后要发送的广播
                    int id = (int) (System.currentTimeMillis() / 1000);
                    PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent)
                            .setContentTitle("下载完成")
                            .setContentText("点击安装/查看")
                            .setAutoCancel(true);//设置通知被点击一次是否自动取消
                    notificationManager.notify(1, builder.build());
                    // 下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(call, e);
                } finally {
                    FileUtils.closeStream(is, fos);
                }
            }
        });
    }

    /**
     * @param url 文件路径
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        if (url == null || !url.contains("/") || !url.toLowerCase().endsWith(".apk") || url.endsWith("/")) {
            return BuildConfig.APPLICATION_ID + ".apk";
        } else {
            return url.substring(url.lastIndexOf("/") + 1);
        }
    }

    /**
     * binder
     */
    public class DownloadBinder extends Binder {
        @SuppressWarnings("unused")
        public void backgroundDown(String url, String saveDir, OnDownloadListener listener) {
            download(url, saveDir, listener);
        }

    }

}
