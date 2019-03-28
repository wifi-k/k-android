package cn.treebear.kwifimanager.activity.bindap;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.bindap.settings.ChooseNetworkStyleActivity;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.contract.BindNodeConstract;
import cn.treebear.kwifimanager.mvp.server.presenter.BindNodePresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.CountObserver;
import cn.treebear.kwifimanager.util.CountUtil;
import cn.treebear.kwifimanager.util.NetWorkUtils;
import cn.treebear.kwifimanager.widget.dialog.LoadingProgressDialog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 */
public class BindAction1Activity extends BaseActivity<BindNodeConstract.Presenter, Object> implements BindNodeConstract.View {

    @BindView(R.id.tv_mid_info)
    TextView tvMidInfo;
    @BindView(R.id.btn_bottom)
    Button btnConfirm;
    private int wifiState;
    private TMessageDialog tMessageDialog;
    private WifiManager wifiManager;
    private Disposable mDisposable;

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
    public BindNodeConstract.Presenter getPresenter() {
        return new BindNodePresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtils.permission(PermissionConstants.LOCATION)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        checkWiFi();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showLong(R.string.refuse_loaction_permission);
                    }
                }).request();
    }

    private void checkWiFi() {
        if (!Check.hasContent(MyApplication.getAppContext().getDeviceInfo().getId())) {
            WiFiHttpClient.tryToSignInWifi(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
                @Override
                public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                    change2Bind();
                }

                @Override
                public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                    scanAgain();
                }
            });
        } else {
            change2Bind();
        }
    }

    private void change2Bind() {
        if (Check.hasContent(MyApplication.getAppContext().getDeviceInfo().getId())) {
            tvMidInfo.setText(String.format("您已连接wifi名称为“%s”的设备，点击立即绑定设备", NetWorkUtils.getRealSSIDWhenWifi(this)));
            btnConfirm.setText(R.string.bind_now);
        }
    }

    private void scanAgain() {
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

    @OnClick(R.id.btn_bottom)
    public void onBtnBottomClicked() {
        if (Check.hasContent(MyApplication.getAppContext().getDeviceInfo().getId())) {
            showLoading();
            mPresenter.bindNode(MyApplication.getAppContext().getDeviceInfo().getId());
        } else {
            if (NetWorkUtils.isWifiConnected(this)) {
                WiFiHttpClient.tryToSignInWifi(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                        showLoading();
                        mPresenter.bindNode(MyApplication.getAppContext().getDeviceInfo().getId());
                    }

                    @Override
                    public void onFailed(BaseResponse response, String resultMsg, int resultCode) {

                    }
                });

                String wifiSSID = NetWorkUtils.getSSIDWhenWifi(this);
                if (Check.hasContent(wifiSSID)) {
                    if (wifiSSID.contains(Config.Text.AP_NAME_START)) {
                        mPresenter.bindNode(MyApplication.getAppContext().getDeviceInfo().getId());
                    } else {
                        ToastUtils.showShort(R.string.connect_xiaok_tips1);
                    }
                }
            } else {
                ToastUtils.showShort(R.string.connect_xiaok_tips1);
            }
        }
    }

    @Override
    public void onLoadData(Object resultData) {
        ToastUtils.showShort(R.string.bind_success);
        hideLoading();
        MyApplication.getAppContext().getUser().setNodeSize(1);
        startActivity(ChooseNetworkStyleActivity.class);
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        finish();
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        hideLoading();
        switch (resultCode) {
            case ApiCode.INVALID_PARAM:
                ToastUtils.showShort(R.string.this_node_has_bound);
                break;
            default:
                ToastUtils.showShort(R.string.bind_fail_please_retry);
                break;
        }
    }

    private void scanWifi() {
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
                        public void onClickLeft(android.view.View view) {
                            tMessageDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(android.view.View view) {
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
                        public void onClickLeft(android.view.View view) {
                            tMessageDialog.dismiss();
                            ActivityStackUtils.finishAll(Config.Tags.TAG_FIRST_BIND_WIFI);
                        }

                        @Override
                        public void onClickRight(android.view.View view) {
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
                        public void onClickLeft(android.view.View view) {
                            tMessageDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(android.view.View view) {
                            tMessageDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        dismiss(tMessageDialog);
        dispose(mDisposable);
        super.onDestroy();
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}
