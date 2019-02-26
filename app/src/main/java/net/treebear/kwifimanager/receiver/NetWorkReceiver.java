package net.treebear.kwifimanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.blankj.utilcode.util.ToastUtils;

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
                ToastUtils.showShort("您已连接WiFi");
            }
            if (!isWifi && hasOnWifi){
                ToastUtils.showShort("您已断开WiFi连接");
                hasOnWifi = false;
            }
        }
    }
}
