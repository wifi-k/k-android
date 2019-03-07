package net.treebear.kwifimanager.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateFormatUtils {

    public static final long MILL = 1L;
    public static final long SECONDS = 1000 * MILL;
    public static final long MINUTE = 60 * SECONDS;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;

    private static final String S_MILL = "毫秒";
    private static final String S_SECENDS = "秒";
    private static final String S_MINUTE = "分钟";
    private static final String S_HOUR = "小时";
    private static final String S_DAY = "天";
    private static final String S_LESS_SECENDS = "小于1秒";

    private DateFormatUtils() {
    }

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdfMDHmm = new SimpleDateFormat("M月d日 h:mm");

    public static void main(String[] args) {
        System.out.println(formatMDHmm(System.currentTimeMillis() - 86400 * 1000L));
    }

    /**
     * 格式化时间
     *
     * @param mills 时间毫秒值
     * @return MD_Hmm格式时间
     */
    public static String formatMDHmm(long mills) {
        GregorianCalendar calendar = new GregorianCalendar(Locale.CHINA);
        calendar.setTimeInMillis(mills);
        return sdfMDHmm.format(calendar.getTime());
    }

    /**
     * 将毫秒值转换为时长（仅保留最大单位）
     */
    public static String mill2Time(long mill) {
        String time;
        if (mill < SECONDS) {
            time = S_LESS_SECENDS;
        } else if (mill < MINUTE) {
            time = mill / SECONDS + S_SECENDS;
        } else if (mill < HOUR) {
            time = mill / MINUTE + S_MINUTE;
        } else if (mill < DAY) {
            time = mill / HOUR + S_HOUR;
        } else {
            time = mill / DAY + S_DAY;
        }
        return time;
    }

    /**
     * 将毫秒值转换为时长（从大到小保留3个单位）
     */
    public static String mill2FullTime(long mill) {
        String time;
        if (mill < SECONDS) {
            time = S_LESS_SECENDS;
        } else if (mill < MINUTE) {
            time = mill / SECONDS + S_SECENDS;
        } else if (mill < HOUR) {
            long sec = (mill % MINUTE) / SECONDS;
            // x分钟(y>0)秒
            time = mill / MINUTE + S_MINUTE + (sec > 0 ? sec + S_SECENDS : "");
        } else if (mill < DAY) {
            long minute = (mill % HOUR) / MINUTE;
            long sece = ((mill % HOUR) % MINUTE) / SECONDS;
            // x小时(y=0分钟z>0秒) 或 x小时(y>0分钟) 或 x小时
            time = mill / HOUR + S_HOUR + (sece > 0 ? minute + S_MINUTE + sece + S_SECENDS : (minute > 0 ? minute + S_MINUTE : ""));
        } else {
            long hour = (mill % DAY) / HOUR;
            long mnt = ((mill % DAY) % HOUR) / MINUTE;
            time = mill / DAY + S_DAY + (mnt > 0 ? hour + S_HOUR + mnt + S_MINUTE : (hour > 0 ? hour + S_HOUR : ""));
        }
        return time;

    }

}
