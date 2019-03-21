package net.treebear.kwifimanager.http;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

public class ImageUploadManager {
    private static UploadManager uploadManager;

    public static UploadManager getInstance() {
        synchronized (ImageUploadManager.class) {
            if (uploadManager == null) {
                Configuration config = new Configuration.Builder()
                        .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                        .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                        .connectTimeout(10)           // 链接超时。默认10秒
                        .useHttps(true)               // 是否使用https上传域名
                        .responseTimeout(60)          // 服务器响应超时。默认60秒
                        .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                        .build();
                // 重用uploadManager。一般地，只需要创建一个uploadManager对象
                uploadManager = new UploadManager(config);
            }
            return uploadManager;
        }
    }
}
