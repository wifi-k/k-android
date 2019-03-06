package net.treebear.kwifimanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.http.WiFiHttpClient;
import net.treebear.kwifimanager.util.NetWorkUtils;

/**
 * @author Administrator
 */
public class NetWorkReceiver extends BroadcastReceiver {
    private boolean hasOnWifi = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            boolean isWifi = networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            if (isWifi && !hasOnWifi) {
                hasOnWifi = true;
                if (NetWorkUtils.isConnectXiaoK(context)) {
                    ToastUtils.showShort(R.string.has_xiaok_connected);
                    WiFiHttpClient.xiaokOnline();
                } else {
                    ToastUtils.showShort(R.string.has_wifi_connected);
                }
            }
            if (!isWifi && hasOnWifi) {
                ToastUtils.showShort("您已断开WiFi连接");
                WiFiHttpClient.xiaokOffline();
                hasOnWifi = false;
            }
        }
    }
}
