package cn.treebear.kwifimanager.config;

/**
 * 所有key值存储
 *
 * @important 请不要通过实现的方式引入
 */
public interface Keys {
    //----------------------request传输key--------------------//
    String MOBILE = "mobile";
    String TYPE = "type";
    String VERIFY_CODE = "vcode";
    String PASSWORD = "passwd";
    String NAME = "name";
    String SSID0 = "ssid0";
    String SSID = "ssid";
    String PASSWD_WIFI = "passwd";
    String IP = "ip";
    String NET_MASK = "netmask";
    String GATEWAY = "gateway";
    String DNS1 = "dns1";
    String DNS2 = "dns2";
    String BSSID = "bssid";
    String AVATAR = "avatar";
    String NODE_ID = "nodeId";
    String PAGE_NO = "pageNo";
    String PAGE_SIZE = "pageSize";
    String FREQ = "freq";
    String FILTER = "filter";
    String INVITE_CODE = "inviteCode";
    String USER_NAME = "userName";
    String USER_ID = "userId";
    String OP = "op";
    String WIFI = "wifi";
    String MAC = "mac";
    String NOTE = "note";
    String BLOCK = "block";

    /**
     * ----------------------intent传输key--------------------
     */
    String TITLE = "title";
    String CONFIRM_BUTTON_TEXT = "confirm_button_text";

    String DEVICE_INFO = "device_info";
    /**
     * 传时间限制序列化对象
     */
    String TIME_LIMIT_BEAN = "time_limit_bean";
    /**
     * 禁用app计划
     */
    String BAN_APP_PLAN = "ban_app_plan";
    String TIME_LIMIT_TIME = "time_limit_time";
    String PARENT_CONTROL_DEVICE = "parent_control_device";

    String SETTINGS_ITEM_DATA = "settings_item_data";

    String POSITION = "position";

    String QI_NIU_TOKEN = "mQiNiuToken";

    String IS_MODIFY_PASSWORD = "is_modify_password";

    String FIRST_IN = "first_in_app";

    String START_TIME = "start_time";

    String END_TIME = "end_time";

}
