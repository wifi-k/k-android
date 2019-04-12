package cn.treebear.kwifimanager.activity.bindap;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
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
import cn.treebear.kwifimanager.util.SharedPreferencesUtil;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.LoadingProgressDialog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 */
public class BindAction1Activity extends BaseActivity<BindNodeConstract.Presenter, Object> implements BindNodeConstract.View {

    @BindView(R2.id.tv_mid_info)
    TextView tvMidInfo;
    @BindView(R2.id.btn_bottom)
    Button btnConfirm;
    private int bindType = Values.TYPE_FIRST_INCREASE_NODE;
    private TMessageDialog tMessageDialog;
    private WifiManager wifiManager;
    private Disposable mDisposable;
    private TMessageDialog hasBindDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_bind_step1;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            bindType = params.getInt(Keys.TYPE, Values.TYPE_FIRST_INCREASE_NODE);
        }
    }

    @Override
    public BindNodeConstract.Presenter getPresenter() {
        return new BindNodePresenter();
    }

    @Override
    protected void initView() {
//        setTitleBack(bindType == 1 ? R.string.append_xiaok : R.string.setting, R.string.skip_set_network);
        setTitleBack(bindType == 1 ? R.string.append_xiaok : R.string.setting);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }

    @Override
    protected void onTitleRightClick() {
//        if (Check.hasContent(WiFiHttpClient.getWifiDeviceInfo().getId())) {
////            showLoading(getString(R.string.bind_ing));
////            mPresenter.bindNode(WiFiHttpClient.getWifiDeviceInfo().getId());
////        } else {
////            ToastUtils.showShort("暂未获取到nodeId，请稍后再试");
////        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtils.permission(PermissionConstants.LOCATION)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        if (WiFiHttpClient.getNeedLogin()) {
                            checkWiFi();
                        } else {
                            change2Bind();
                        }
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showLong(R.string.refuse_loaction_permission);
                    }
                }).request();
    }

    private void checkWiFi() {
        LoadingProgressDialog.showProgressDialog(this, getString(R.string.scanning));
        if (!NetWorkUtils.isXiaoKSignIn()) {
            WiFiHttpClient.getInstance().tryToSignInWifi(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
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
        hideLoading();
        if (Check.hasContent(WiFiHttpClient.getWifiDeviceInfo().getId())) {
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

    @OnClick(R2.id.btn_bottom)
    public void onBtnBottomClicked() {
        TLog.w("OkHttp", WiFiHttpClient.getWifiDeviceInfo().getId());
        if (Check.hasContent((String) SharedPreferencesUtil.getParam(SharedPreferencesUtil.NODE_ID, ""))) {
//            MyApplication.getAppContext().getUser().setNodeSize(1);
            if (bindType == Values.TYPE_FIRST_INCREASE_NODE) {
                ToastUtils.showShort(R.string.bind_success);
                hideLoading();
                startActivity(ChooseNetworkStyleActivity.class);
                finish();
            } else {
                showLoading(getString(R.string.bind_ing));
                mPresenter.bindNode(WiFiHttpClient.getWifiDeviceInfo().getId());
            }
        } else {
            if (MyApplication.getAppContext().getUser().getNodeSize() == 0) {
                notXiaoKDialog();
            }
        }
//        if (Check.hasContent(WiFiHttpClient.getWifiDeviceInfo().getId())) {
//            showLoading();
//            mPresenter.bindNode(WiFiHttpClient.getWifiDeviceInfo().getId());
//        } else {
//            if (NetWorkUtils.isWifiConnected(this)) {
//                WiFiHttpClient.tryToSignInWifi(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
//                        showLoading();
//                        mPresenter.bindNode(WiFiHttpClient.getWifiDeviceInfo().getId());
//                    }
//
//                    @Override
//                    public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
//
//                    }
//                });
//                String wifiSSID = NetWorkUtils.getSSIDWhenWifi(this);
//                if (Check.hasContent(wifiSSID)) {
//                    if (wifiSSID.contains(Config.Text.AP_NAME_START)) {
//                        mPresenter.bindNode(WiFiHttpClient.getWifiDeviceInfo().getId());
//                    } else {
//                        ToastUtils.showShort(R.string.connect_xiaok_tips1);
//                    }
//                }
//            } else {
//                notXiaoKDialog();
//            }
//        }
    }

    @Override
    public void onLoadData(Object resultData) {
        ToastUtils.showShort(R.string.bind_success);
        hideLoading();
        MyApplication.getAppContext().getUser().setNodeSize(1);
        if (bindType == Values.TYPE_FIRST_INCREASE_NODE) {
            startActivity(ChooseNetworkStyleActivity.class);
        }
        finish();
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        super.onLoadFail(response, resultMsg, resultCode);
        switch (resultCode) {
            case ApiCode.INVALID_PARAM:
                ToastUtils.showShort(R.string.this_node_has_bound);
                break;
            case ApiCode.USR_INVALID:
                showHasBindDialog();
                break;
            default:
                ToastUtils.showShort(R.string.bind_fail_please_retry);
                break;
        }
    }

    /**
     * 已绑定过当前设备
     */
    private void showHasBindDialog() {
        if (hasBindDialog == null) {
            hasBindDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.tips)
                    .content(R.string.you_has_bound_this_try_other)
                    .left(R.string.cancel)
                    .right(R.string.goto_connect)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            hasBindDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            NetWorkUtils.gotoWifiSetting(BindAction1Activity.this);
                            hasBindDialog.dismiss();
                        }
                    });
        }
        hasBindDialog.show();
    }

    /**
     * 扫描Wifi
     */
    private void scanWifi() {
        if (wifiManager != null) {
            wifiManager.startScan();
        }
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

    /**
     * 没有小K的处理
     */
    private void whenNotXiaoK() {
        // 非小K的处理
        if (NetWorkUtils.searchXiaoKInAround(this) > 0) {
//                周围有小K
            notXiaoKDialog();
        } else {
//                周围没有小K
            initMessageDialog();
            tMessageDialog.content(String.format("暂时没有发现wifi名称为“xiaok-XXXX”的设备,请先启动并连接设备。"))
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(android.view.View view) {
                            tMessageDialog.dismiss();
                            ActivityStackUtils.finishAll(Config.Tags.TAG_FIRST_BIND_WIFI);
                        }

                        @Override
                        public void onClickRight(android.view.View view) {
                            tMessageDialog.dismiss();
                            NetWorkUtils.gotoWifiSetting(BindAction1Activity.this);
                        }
                    }).show();
        }
    }

    private void notXiaoKDialog() {
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
    }

    /**
     * 初始化通用弹窗
     */
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
        dismiss(tMessageDialog, hasBindDialog);
        dispose(mDisposable);
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        super.onDestroy();
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
    }
}
