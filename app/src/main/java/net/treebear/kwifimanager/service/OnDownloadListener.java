package net.treebear.kwifimanager.service;

import java.io.File;

import okhttp3.Call;

public interface OnDownloadListener {
    /**
     * 下载成功
     */
    void onDownloadSuccess(File file);

    /**
     * @param progress 下载进度
     */
    void onDownloading(int progress);

    /**
     * 下载失败
     */
    void onDownloadFailed(Call call, Exception e);
}