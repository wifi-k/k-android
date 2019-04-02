package cn.treebear.kwifimanager.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;

import java.util.List;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.config.Config;

/**
 * 网络管理工具
 *
 * @author Tinlone
 */
public class NetWorkUtils {

    private static NetworkInfo getNetworkInfo(@NonNull Context paramContext) {
        ConnectivityManager systemService = (ConnectivityManager) paramContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo localNetworkInfo = null;
        if (systemService != null) {
            localNetworkInfo = systemService.getActiveNetworkInfo();
        }
        return localNetworkInfo;
    }

    /**
     * 检查网络是否可用
     *
     * @param paramContext paramContext
     * @return return
     */
    public static boolean isNetConnected(@NonNull Context paramContext) {

        return (getNetworkInfo(paramContext) != null) && (getNetworkInfo(paramContext).isAvailable());
    }

    /**
     * 检测wifi是否连接
     */
    public static boolean isWifiConnected(@NonNull Context context) {
        WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        return mWifiManager.isWifiEnabled() && ipAddress != 0;
    }

    /**
     * 检测3G是否连接
     */
    public static boolean is4gConnected(Context context) {
        return getNetworkInfo(context) != null && getNetworkInfo(context).getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * Wifi环境下获取当前Wifi名称
     */
    public static String getSSIDWhenWifi(Context context) {
        if (isWifiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.getConnectionInfo().getSSID();
//            TLog.i(getNetworkInfo(context).getSubtypeName());
//            return getNetworkInfo(context) != null ? getNetworkInfo(context).getSubtypeName() : "";
        }
        return "";
    }

    /**
     * Wifi环境下获取当前Wifi名称
     */
    public static String getRealSSIDWhenWifi(Context context) {
        if (isWifiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return getRealSSID(wifiManager.getConnectionInfo().getSSID());
//            TLog.i(getNetworkInfo(context).getSubtypeName());
//            return getNetworkInfo(context) != null ? getNetworkInfo(context).getSubtypeName() : "";
        }
        return "";
    }

    /**
     * Wifi环境下获取当前Wifi \\ mac// bssid
     */
    public static String getBSSIDWhenWifi(Context context) {
        if (isWifiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.getConnectionInfo().getBSSID();
        }
        return "";
    }

    /**
     * 是否连接到小K
     */
    public static boolean isSameLikeXiaoK(Context context) {
        return isWifiConnected(context) && getSSIDWhenWifi(context).contains(Config.Text.AP_NAME_START);
    }

    /**
     * 小K 已登录
     */
    public static boolean isXiaoKSignIn() {
        return Check.hasContent(MyApplication.getAppContext().getDeviceInfo().getToken());
    }

    /**
     * 是否当前连接nodeId为 @param nodeId 的设备
     */
    public static boolean isCurrentXiaoK(String nodeId) {
        return isXiaoKSignIn()
                && MyApplication.getAppContext().getDeviceInfo().getId().equals(nodeId);
    }

    /**
     * WIFI环境下获取扫描列表
     */
    public static List<ScanResult> getWifiList(Context context) {
        if (isWifiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.getScanResults();
        }
        return null;
    }

    /**
     * 寻找信号范围内的小K设备
     *
     * @return 设备数量
     */
    public static int searchXiaoKInAround(Context context) {
        List<ScanResult> wifiList = getWifiList(context);
        TLog.i(wifiList);
        int number = 0;
        if (wifiList != null) {
            for (ScanResult scanResult : wifiList) {
                TLog.i(scanResult);
                if (scanResult.SSID != null && scanResult.SSID.startsWith(Config.Text.AP_NAME_START)) {
                    number++;
                }
            }
        }
        return number;
    }

    /**
     * 监测附近有ssid 位name的wifi
     */
    public static boolean scanWifiByName(Context context, String name) {
        List<ScanResult> wifiList = getWifiList(context);
        TLog.i(wifiList);
        if (wifiList != null) {
            for (ScanResult scanResult : wifiList) {
                TLog.i(scanResult);
                if (scanResult.SSID != null && getRealSSID(scanResult).equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getRealSSID(ScanResult scanResult) {
        String so0 = scanResult.SSID;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (scanResult.SSID.startsWith("\"") && scanResult.SSID.endsWith("\"")) {
                so0 = scanResult.SSID.substring(1, scanResult.SSID.length() - 1);
            }
        }
        return so0;
    }

    public static String getRealSSID(String ssid0) {
        String so0 = ssid0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (ssid0.startsWith("\"") && ssid0.endsWith("\"")) {
                so0 = ssid0.substring(1, ssid0.length() - 1);
            }
        }
        return so0;
    }

    /**
     * 寻找周围信号最强的小K设备
     */
    public static ScanResult searchStrongXiaoKInAround(Context context) {
        List<ScanResult> wifiList = getWifiList(context);
        ScanResult temp = null;
        if (wifiList != null) {
            int mastStrong = 0;
            for (ScanResult scanResult : wifiList) {
                int level = WifiManager.calculateSignalLevel(scanResult.level, 100);
                if (level > mastStrong) {
                    mastStrong = level;
                    temp = scanResult;
                }
            }
        }
        return temp;
    }

    /**
     * 进入WIFI设置界面
     */
    public static void gotoWifiSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
