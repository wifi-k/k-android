package net.treebear.kwifimanager.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.NonNull;

import net.treebear.kwifimanager.config.Config;

import java.util.List;

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
    public static boolean isConnectXiaoK(Context context) {
        return isWifiConnected(context) && getSSIDWhenWifi(context).contains(Config.Text.AP_NAME_START);
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
