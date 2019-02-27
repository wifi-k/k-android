package net.treebear.kwifimanager.config;

import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.ColorInt;

import net.treebear.kwifimanager.BuildConfig;

/**
 * @author Tinlone
 * @date 2018/3/23.
 */

public interface Config {

    interface BaseUrls {
        String BASE_URL = "http://test.user.famwifi.com/api/";
    }

    interface Tags{
        String TAG_SIGN_ACCOUNT = "account.sign_in_up";
    }

    interface Colors{
       @ColorInt
       int MAIN = Color.parseColor("#26DCBC");
        @ColorInt
       int LINE = Color.parseColor("#F2F2F2");
    }
    /**
     * 数字常量
     */
    interface Numbers {
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
        int MIN_PWD_LENGTH = 6;
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
    }

    /**
     * 字符常量
     */
    interface Strings {
        String VERIFY_CODE_FORMAT = "%sS后重新获取";
        String GET_VERIFY_CODE = "获取验证码";
        String TIPS = "提示";
        String HAS_ACCOUNT = "该手机号已注册，请立即登录";
        String CANCEL = "取消";
        String SIGN_IN_NOW = "立即登录";
    }

    interface ResponseCode{
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

    /**
     * 文件目录
     */
    interface Path {
        String PRIVATE = String.format("%s/%s", Environment.getExternalStorageState(), BuildConfig.APP_NAME);
        String FILE = PRIVATE + "/files/";
        String CACHE = PRIVATE + "/cache/";
        String MEDIA = PRIVATE + "/media/";
    }

}
