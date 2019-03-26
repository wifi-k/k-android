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

    /**
     * ----------------------REQUEST CODE   RESULT CODE--------------------
     */
    int REQUEST_SENIOR_SETTING = 0x0001;
    /**
     * request code 相机拍照
     */
    int REQUEST_SYSTEM_CAMERA = 0x0011;
    /**
     * request code 相册获取
     */
    int REQUEST_SYSTEM_GALLERY = 0x0012;
    /**
     * request code 选择小k
     */
    int REQUEST_SELECT_NODE = 0x0002;
    /**
     * 获取控制时间
     */
    int REQUEST_EDIT_TIME = 0x0002;
    /**
     * 获取控制设备
     */
    int REQUEST_EDIT_DEVICE = 0x0003;
    /**
     * 获取控制app
     */
    int REQUEST_BAN_APP = 0x0004;
    /**
     * 频率 -- 所有
     */
    int FREQ_ALL = 0;
    /**
     * 频率 -- 2.4G
     */
    int FREQ_2D4_G = 1;
    /**
     * 频率 -- 5G
     */
    int FREQ_5_G = 2;

}
