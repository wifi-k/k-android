package net.treebear.kwifimanager.activity.bindap;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.home.settings.ChooseNetworkStyleActivity;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.config.Values;
import net.treebear.kwifimanager.http.WiFiHttpClient;
import net.treebear.kwifimanager.receiver.WiFiStateReceiver;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.CountObserver;
import net.treebear.kwifimanager.util.CountUtil;
import net.treebear.kwifimanager.util.NetWorkUtils;
import net.treebear.kwifimanager.util.TLog;
import net.treebear.kwifimanager.widget.LoadingProgressDialog;
import net.treebear.kwifimanager.widget.TMessageDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 */
public class BindAction1Activity extends BaseActivity {

    @BindView(R.id.tv_mid_info)
    TextView tvMidInfo;
    @BindView(R.id.btn_bottom)
    Button btnConfirm;
    private int wifiState;
    private TMessageDialog tMessageDialog;
    private boolean isScanned;
    private WifiManager wifiManager;
    private Disposable mDisposable;
    private WiFiStateReceiver wiFiStateReceiver;

    @Override
    public int layoutId() {
        return R.layout.activity_bind_step1;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            wifiState = params.getInt(Keys.TYPE, Values.CONNECT_WIFI_NONE);
        }
    }

    @Override
    protected void initView() {
        if (BuildConfig.DEBUG) {
            MyApplication.getAppContext().getUser().setAuthStatus(1);
        }
        setTitleBack(R.string.setting);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
//        @StringRes int infoTextId;
//        switch (wifiState) {
//            case Values.CONNET_WIFI_XIAOK:
//                infoTextId = R.string.online_wifi_bind_xiaok;
//                break;
//            case Values.CONNECT_WIFI_OTHER:
//                infoTextId = R.string.app_name;
//                break;
//            default:
//                infoTextId = R.string.app_name;
//                break;
//        }
//        tvMidInfo.setText(getString(infoTextId));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetWorkUtils.isConnectXiaoK(this)) {
            WiFiHttpClient.tryToSignInWifi(null);
            // 连接到小K的处理
            tvMidInfo.setText(String.format("您已连接wifi名称为“%s”的设备，点击立即绑定设备", NetWorkUtils.getSSIDWhenWifi(this)));
            btnConfirm.setText(R.string.bind_now);
        } else {
            PermissionUtils.permission(PermissionConstants.LOCATION)
                    .callback(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            scanWifi();
                        }

                        @Override
                        public void onDenied() {

                        }
                    }).request();
        }
    }

    @OnClick(R.id.btn_bottom)
    public void onBtnBottomClicked() {
//        if (NetWorkUtils.isWifiConnected(this)) {
//            String wifiSSID = NetWorkUtils.getSSIDWhenWifi(this);
//            if (Check.hasContent(wifiSSID)) {
//                if (wifiSSID.contains(Config.Text.AP_NAME_START)) {
//                    startActivity(ChooseNetworkStyleActivity.class);
//                } else {
//                    ToastUtils.showShort(R.string.connect_xiaok_tips1);
//                }
//            }
//        } else {
//            ToastUtils.showShort(R.string.connect_xiaok_tips1);
//        }
        //test startActivity(ChooseNetworkStyleActivity.class);
        startActivity(ChooseNetworkStyleActivity.class);
    }

    private void scanWifi() {
        TLog.valueOf("-----------------" + NetWorkUtils.isConnectXiaoK(this));
        if (wifiManager != null) {
            wifiManager.startScan();
        }
        LoadingProgressDialog.showProgressDialog(this, getString(R.string.scanning));
        CountUtil.numberDown(3, new CountObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onComplete() {
                whenNotXiaoK();
                hideLoading();
            }
        });
    }

    private void whenNotXiaoK() {
        // 非小K的处理
        if (NetWorkUtils.searchXiaoKInAround(this) > 0) {
//                周围有小K
            initMessageDialog();
            tMessageDialog.content(String.format("请前往设置连接名称为“xiaok-XXXX”的WiFi，然后再绑定设备。"))
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            tMessageDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            NetWorkUtils.gotoWifiSetting(BindAction1Activity.this);
                            tMessageDialog.dismiss();
                        }
                    }).show();
        } else {
//                周围没有小K
            initMessageDialog();
            tMessageDialog.content(String.format("暂时没有发现wifi名称为“xiaok-XXXX”的设备,请先启动设备。"))
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            tMessageDialog.dismiss();
//                            ActivityStackUtils.finishAll(Config.Tags.TAG_FIRST_BIND_WIFI);
                        }

                        @Override
                        public void onClickRight(View view) {
                            tMessageDialog.dismiss();
                            // todo test
                            startActivity(ChooseNetworkStyleActivity.class);
//                            ActivityStackUtils.finishAll(Config.Tags.TAG_FIRST_BIND_WIFI);
                        }
                    }).show();
        }
    }

    private void initMessageDialog() {
        if (tMessageDialog == null) {
            tMessageDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.online_tips)
                    .content("")
                    .left(R.string.cancel)
                    .right(R.string.confirm)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            tMessageDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            tMessageDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose(mDisposable);
        if (tMessageDialog != null) {
            tMessageDialog.dismiss();
        }
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}
