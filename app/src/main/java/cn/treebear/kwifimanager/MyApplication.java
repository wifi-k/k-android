package cn.treebear.kwifimanager;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.multidex.MultiDexApplication;

import cn.treebear.kwifimanager.activity.account.launchAccountActivity;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.http.HttpClient;
import cn.treebear.kwifimanager.receiver.NetWorkReceiver;
import cn.treebear.kwifimanager.receiver.OpenFileReceiver;
import cn.treebear.kwifimanager.util.NetWorkUtils;
import cn.treebear.kwifimanager.util.TLog;
import io.realm.Realm;

/**
 * @author Tinlone
 * @date 2018/3/23.
 * I cannot choose the best. The best chooses me.
 */
public class MyApplication extends MultiDexApplication {

    private static MyApplication mContext;

    public static MyApplication getAppContext() {
        return mContext;
    }

    private boolean needUpdateUserInfo = true;

    private String currentSelectNode;

    /**
     * 若不保存用户信息情况下，仅单次记录用户信息
     */
    private ServerUserInfo user;

    private WifiDeviceInfo info;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        dealUncaughtException();
        Realm.init(this);
        registerReceiver(new OpenFileReceiver(), new IntentFilter(BuildConfig.APPLICATION_ID + ".open_file"));
        registerNetReceiver();
        if (BuildConfig.DEBUG) {
            test();
        }
    }

    private void test() {
        String ssid0 = NetWorkUtils.getSSIDWhenWifi(this);
        String so0 = ssid0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (ssid0.startsWith("\"") && ssid0.endsWith("\"")) {
                so0 = ssid0.substring(1, ssid0.length() - 1);
            }
        }
        TLog.e("ssid0 = %s ; so0 = %s", ssid0, so0);
    }

    private void registerNetReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        NetWorkReceiver mNetWorkReceiver = new NetWorkReceiver();
        registerReceiver(mNetWorkReceiver, intentFilter);
    }


    private void dealUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            //开发期间打印报错日志
            if (BuildConfig.DEBUG) {
                TLog.e(e);
            } else {
                Intent intent = new Intent(mContext, launchAccountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    public void savedUser(ServerUserInfo user) {
        this.user = user;
        HttpClient.updataApiToken(user.getToken());
    }

    public ServerUserInfo getUser() {
        if (user == null) {
            user = new ServerUserInfo();
        }
        return user;
    }

    public WifiDeviceInfo getDeviceInfo() {
        if (info == null) {
            info = new WifiDeviceInfo();
        }
        return info;
    }

    public void setNeedUpdateUserInfo(boolean need) {
        needUpdateUserInfo = need;
    }

    public boolean isNeedUpdateUserInfo() {
        return needUpdateUserInfo;
    }

    public void saveDeviceInfo(WifiDeviceInfo info) {
        this.info = info;
    }

    public String getCurrentSelectNode() {
        return currentSelectNode;
    }

    public void setSelectNode(String node) {
        currentSelectNode = node;
    }

    /**
     * @return 当前用户是否已认证
     */
    public boolean hasBoundNode() {
        return getUser().getNodeSize() > 0;
    }
}
