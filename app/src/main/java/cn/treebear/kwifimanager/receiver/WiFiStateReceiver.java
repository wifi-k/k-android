package cn.treebear.kwifimanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import cn.treebear.kwifimanager.util.TLog;

/**
 * @author Administrator
 */
public class WiFiStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_DISABLED);
        boolean isScanned = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        switch (wifistate) {
            case WifiManager.WIFI_STATE_DISABLED:
                //wifi已关闭
                TLog.i("onReceive: wifi已关闭");
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                //wifi已打开
                TLog.i("onReceive: wifi已打开");
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                //wifi正在打开
                TLog.i("onReceive: wifi正在打开");
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                TLog.i("onReceive: wifi正在关闭");
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                TLog.i("onReceive: wifi未知");
                break;
            default:
                break;
        }
    }
}
