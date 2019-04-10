package cn.treebear.kwifimanager.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

import cn.treebear.kwifimanager.util.FileUtils;

public class OpenFileReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String path = intent.getStringExtra("path");
            if (PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Intent dealIntent = FileUtils.getFileOpenIntent(path);
                if (dealIntent != null) {
                    dealIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(dealIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
