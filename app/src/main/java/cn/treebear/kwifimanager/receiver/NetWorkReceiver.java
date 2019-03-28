package cn.treebear.kwifimanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.blankj.utilcode.util.ToastUtils;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.NetWorkUtils;

/**
 * @author Administrator
 */
public class NetWorkReceiver extends BroadcastReceiver {
    private boolean hasOnWifi = false;

    private boolean isTryingSign = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            boolean isWifi = networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            if (isWifi && !hasOnWifi) {
                hasOnWifi = true;
                if (NetWorkUtils.isSameLikeXiaoK(context)) {
                    ToastUtils.showShort(R.string.has_xiaok_connected);
                    WiFiHttpClient.xiaokOnline();
                } else {
                    ToastUtils.showShort(R.string.has_wifi_connected);
                }
                tryToSignWifi();
            }
            if (!isWifi && hasOnWifi) {
                ToastUtils.showShort("您已断开WiFi连接");
                WiFiHttpClient.xiaokOffline();
                hasOnWifi = false;
            }
        }
    }

    private void tryToSignWifi() {
        if (!isTryingSign) {
            isTryingSign = true;
            if (!Check.hasContent(MyApplication.getAppContext().getDeviceInfo().getId())) {
                WiFiHttpClient.tryToSignInWifi(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                        isTryingSign = false;
                    }

                    @Override
                    public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                        isTryingSign = false;
                    }
                });
            }
        }
    }
}
