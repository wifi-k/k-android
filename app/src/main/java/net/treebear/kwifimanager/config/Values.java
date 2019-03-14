package net.treebear.kwifimanager.config;

/**
 * @author Administrator
 */
public interface Values {
    /**
     * 当前上网为xiaok wifi
     */
    int CONNET_WIFI_XIAOK = 0x0001;
    /**
     * 当前网络为非xiaok wifi
     */
    int CONNECT_WIFI_OTHER = 0x0002;
    /**
     * 当前网络为非wifi
     */
    int CONNECT_WIFI_NONE = 0x0003;
    /**
     * 上网方式 -- 拨号上网
     */
    int ONLINE_TYPE_DIAL = 0x0011;
    /**
     * 上网方式 -- 静态IP上网
     */
    int ONLINE_TYPE_STATIC_IP = 0x0012;
    /**
     * 上网方式 -- 动态ip上网
     */
    int ONLINE_TYPE_DYNAMIC_IP = 0x0013;

    /**----------------------REQUEST CODE   RESULT CODE--------------------*/
    int REQUEST_SENIOR_SETTING = 0x0001;
    /**
     * request code 相机拍照
     */
    int REQUEST_SYSTEM_CAMERA = 0x0011;
    /**
     * request code 相册获取
     */
    int REQUEST_SYSTEM_GALLERY = 0x0012;


}
