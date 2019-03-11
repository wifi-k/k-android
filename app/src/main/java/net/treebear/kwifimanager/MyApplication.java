package net.treebear.kwifimanager;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDexApplication;

import net.treebear.kwifimanager.activity.account.launchAccountActivity;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.http.HttpClient;
import net.treebear.kwifimanager.receiver.NetWorkReceiver;
import net.treebear.kwifimanager.receiver.OpenFileReceiver;
import net.treebear.kwifimanager.util.TLog;

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

    /**
     * 若不保存用户信息情况下，仅单次记录用户信息
     */
    private ServerUserInfo user;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        dealUncaughtException();
        registerReceiver(new OpenFileReceiver(), new IntentFilter(BuildConfig.APPLICATION_ID + ".open_file"));
        registerNetReceiver();
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

    /**
     * @return 当前用户是否已认证
     */
    public boolean hasAuth() {
        return getUser().getAuthStatus() == 1;
    }
}
