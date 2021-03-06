package net.treebear.kwifimanager;


import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

import net.treebear.kwifimanager.activity.LauncherActivity;
import net.treebear.kwifimanager.receiver.OpenFileReceiver;

/**
 * @author Tinlone
 * @date 2018/3/23.
 * I cannot choose the best. The best chooses me.
 */
public class MyApplication extends Application {

    private static MyApplication mContext;

    public static MyApplication getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        dealUncaughtException();
        registerReceiver(new OpenFileReceiver(), new IntentFilter(BuildConfig.APPLICATION_ID + ".open_file"));
    }

    private void dealUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            Intent intent = new Intent(mContext, LauncherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        });
    }
}
