package net.treebear.kwifimanager.config;

import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.ColorInt;

import net.treebear.kwifimanager.BuildConfig;

/**
 * @author Tinlone
 * @date 2018/3/23.
 * @important 请不要通过实现本类中接口的方式引入常量
 */

public final class Config {
    private Config() {
    }

    /**
     * URL数据
     */
    public interface Urls {
        String SERVER_BASE_URL = "http://test.user.famwifi.com/api/";
//        String ROUTER_BASE_URL = "http://192.168.1.1:80/";
        String ROUTER_BASE_URL = "http://192.168.18.254:4000/api/";
        String USER_PROTOCOL = "http://www.baidu.com/";
        String PRIVATE_PROTOCOL = "http://www.baidu.com/";
    }

    /**
     * 栈标志
     */
    public interface Tags {
        String TAG_SIGN_ACCOUNT = "account.sign_in_up";
        String TAG_FORGET_PASSWORD = "account.forget_password";
        String TAG_LAUNCH_ROOT = "account.launch_root";
        String TAG_FIRST_BIND_WIFI = "wifi.first_bind";
    }

    /**
     * 颜色值
     */
    public interface Colors {
        @ColorInt
        int MAIN = Color.parseColor("#26DCBC");
        @ColorInt
        int LINE = Color.parseColor("#F2F2F2");
        @ColorInt
        int TEXT_9B = Color.parseColor("#9B9B9B");
        @ColorInt
        int TEXT_28 = Color.parseColor("#28354C");
        @ColorInt
        int DEVICE_ONLINE = Color.parseColor("#0AD88B");
        @ColorInt
        int DEVICE_OFFLINE = Color.parseColor("#7383A2");
        @ColorInt
        int DEVICE_K_OFFLINE = Color.parseColor("#313E54");
        @ColorInt
        int COLOR_D8 = Color.parseColor("#D8D8D8");
        @ColorInt
        int COLOR_F7 = Color.parseColor("#F7F7F7");
        @ColorInt
        int COLOR_4A5A78 = Color.parseColor("#4A5A78");
        @ColorInt
        int COLOR_C0 = Color.parseColor("#C0C0C0");

    }

    /**
     * 手机类型 、在线情况
     */
    public interface Types {
        int APPLE = 0;
        int ANDROID = 1;
        int PAD = 2;
        //-------------
        int ONLINE = 0;
        int OFFLINE = 1;
        int DISABLE = 2;

        int NET_CHANNEL = 0;
        int WORK_MODEL = 1;
        int SPECTRUM_BANDWIDTH = 2;
    }

    /**
     * 数字常量
     */
    public interface Numbers {
        /**
         * 单页数据量
         */
        int PAGE_SIZE = 10;
        /**
         * 开屏页保持时间
         */
        int ADVERTISEMENT_TIME = 4;
        /**
         * 验证码长度
         */
        int VERIFY_CODE_LENGTH = 4;
        /**
         * 时间格式化长度
         */
        int TIME_TEXT_LENGTH = 8;
        /**
         * 密码长度下限
         */
        int MIN_PWD_LENGTH = 1;
        /**
         * 电话号码长度
         */
        int PHONE_LENGTH = 11;
        /**
         * 密码长度上限
         */
        int MAX_PWD_LENGTH = 16;
        /**
         * 网页刷新等待时间
         */
        int WEB_WAITING_TIME = 30;
        /**
         * 验证码等待时间
         */
        int VERIFY_CODE_WAIT_TIME = 60;
        /**
         * 网页加载成功
         */
        int WEB_LOAD_100 = 95;
        /**
         * 连续时间点击限制
         */
        long CLICK_LIMIT = 666L;
        /**
         * wifi密码下限
         */
        int MIN_WIFI_PASSWORD = 8;
        /**
         * wifi密码上限
         */
        int MAX_WIFI_PASSWORD = 20;
    }

    /**
     * 界面字符常量
     */
    public interface Text {
        String EMPTY = "";
        String VERIFY_CODE_FORMAT = "%sS后重新获取";
        String TIPS = "提示";
        String HAS_ACCOUNT = "该手机号已注册，请立即登录";
        String CANCEL = "取消";
        String SIGN_IN_NOW = "立即登录";
        String SIGN_IN = "登录";
        String AP_NAME_START = "xiaok-";
//        String AP_NAME_START = "Xiaomi";

        String XIAO_K_WIFI_PASSOWRD = "123456";
    }

    /**
     * Toast提示语
     */
    public interface Tips {
        String READ_AND_AGREE_PROTOCOL = "请先阅读并同意《用户协议》《隐私协议》";
        String VERIFY_CODE_ERROR = "验证码有误 请重新输入";
        String VERIFY_SMS_SEND = "验证码发送成功";
        String SIGN_IN_SUCCESS = "登陆成功";
    }

    public interface RequestType {
        /**
         * 注册验证码
         */
        int VERIFY_CODE_SIGN_UP = 1;
        /**
         * 忘记密码验证码
         */
        int VERIFY_CODE_FORGET_PWD = 2;
        /**
         * 修改手机号验证码
         */
        int VERIFY_CODE_MODIFY_PHONE = 3;
        /**
         * 登录验证码
         */
        int VERIFY_CODE_SIGN_IN = 4;

    }

    /**
     * 响应代码
     */
    public interface ServerResponseCode {
        /**
         * 请求成功
         */
        int RESPONSE_OK = 0;

        int RESPONSE_ERR_1 = 1;
        /**
         * TOKEN异常
         */
        int TOKEN_ERROR = 2;
        /**
         * 已注册
         */
        int HAS_SIGN_UP = 9;
    }

    public interface WifiResponseCode {
        //        0-连接成功 1-连接失败 2-正在连接
        int CONNECT_SUCCESS = 0;
        int CONNECT_FAIL = 1;
        int CONNECTING = 2;
    }

    /**
     * 文件目录
     */
    public interface Path {
        String PRIVATE = String.format("%s/%s", Environment.getExternalStorageState(), BuildConfig.APP_NAME);
        String FILE = PRIVATE + "/files/";
        String CACHE = PRIVATE + "/cache/";
        String MEDIA = PRIVATE + "/media/";
    }

}
