package cn.treebear.kwifimanager;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.socialize.PlatformConfig;

import cn.treebear.kwifimanager.activity.account.LaunchAccountActivity;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.http.HttpClient;
import cn.treebear.kwifimanager.receiver.NetWorkReceiver;
import cn.treebear.kwifimanager.receiver.OpenFileReceiver;
import cn.treebear.kwifimanager.util.PhoneStateUtil;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.util.UserInfoUtil;
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
    private boolean needUpdateNodeInfo = true;

    private String currentSelectNode;

    private NodeInfoDetail.NodeBean currentNode;
    /**
     * 若不保存用户信息情况下，仅单次记录用户信息
     */
    private ServerUserInfo user;
    /**
     * 设备信息
     */
    private WifiDeviceInfo info;
    /**
     * 友盟推送客户端
     */
    private PushAgent mPushAgent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        dealUncaughtException();
        initSDKs();
        registerReceivers();
        if (BuildConfig.DEBUG) {
            TLog.phoneInfo(this);
        }
    }

    private void initSDKs() {
        Realm.init(this);
        UMConfigure.init(this, BuildConfig.UMENG_APPKEY, PhoneStateUtil.getChannel(this),
                UMConfigure.DEVICE_TYPE_PHONE, BuildConfig.UMENG_MESSAGE_SECRET);
        initUmeng();
    }

    private void initUmeng() {
        InAppMessageManager.getInstance(this).setInAppMsgDebugMode(BuildConfig.DEBUG);
        PlatformConfig.setWeixin(BuildConfig.WX_APPKEY, BuildConfig.WX_APP_SECRET);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        //获取消息推送代理示例
        mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                TLog.i("注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                TLog.e("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
    }

    private void registerReceivers() {
        registerReceiver(new OpenFileReceiver(), new IntentFilter(BuildConfig.APPLICATION_ID + ".open_file"));
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
                Intent intent = new Intent(mContext, LaunchAccountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    public void savedUser(ServerUserInfo user) {
        this.user = user;
        HttpClient.updataApiToken(user.getToken());
        UserInfoUtil.updateUserInfo(user);
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

    public PushAgent getPushAgent() {
        return mPushAgent;
    }

    /**
     * @return 当前用户是否已认证
     */
    public boolean hasBoundNode() {
        return getUser().getNodeSize() > 0;
    }

    public NodeInfoDetail.NodeBean getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(NodeInfoDetail.NodeBean currentNode) {
        this.currentNode = currentNode;
    }

    public boolean isNeedUpdateNodeInfo() {
        return needUpdateNodeInfo;
    }

    public void setNeedUpdateNodeInfo(boolean needUpdateNodeInfo) {
        this.needUpdateNodeInfo = needUpdateNodeInfo;
    }
}
