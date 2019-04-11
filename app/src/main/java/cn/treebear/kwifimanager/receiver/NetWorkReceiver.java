package cn.treebear.kwifimanager.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import java.util.List;

import cn.treebear.kwifimanager.BuildConfig;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.util.TLog;

import static android.net.wifi.WifiManager.WIFI_STATE_DISABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN;

/**
 * @author Administrator
 */
public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!BuildConfig.APPLICATION_ID.equals(getProcessName(context, android.os.Process.myPid()))) {
            TLog.i("NetWorkReceiver", getProcessName(context, android.os.Process.myPid()) + "非本进程");
            return;
        }
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)) {
                case WIFI_STATE_DISABLED: {
                    WiFiHttpClient.xiaokOffline();
                    break;
                }
                case WIFI_STATE_DISABLING: {
                    break;
                }
                case WIFI_STATE_ENABLED: {
                    if (WiFiHttpClient.getNeedLogin()) {
                        WiFiHttpClient.getInstance().tryToSignInWifi(null);
                        MyApplication.time = System.currentTimeMillis();
                    }
                    break;
                }
                case WIFI_STATE_ENABLING: {
                    break;
                }
                case WIFI_STATE_UNKNOWN: {
                    break;
                }
            }
        }
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            TLog.i("NetWorkReceiver", procInfo.processName);
            if (procInfo.pid == pid) {
                TLog.i("NetWorkReceiver-this", procInfo.processName);
                return procInfo.processName;
            }
        }
        return null;
    }

}
