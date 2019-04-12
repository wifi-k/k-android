package cn.treebear.kwifimanager.activity.toolkit;

import android.util.ArrayMap;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import androidx.gridlayout.widget.GridLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.NodeWifiListBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.contract.NodeOptionSetContract;
import cn.treebear.kwifimanager.mvp.server.presenter.NodeOptionSetPresenter;
import cn.treebear.kwifimanager.mvp.wifi.model.WiFiSettingProxyModel;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DensityUtil;
import cn.treebear.kwifimanager.util.NetWorkUtils;
import cn.treebear.kwifimanager.util.RequestBodyUtils;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import cn.treebear.kwifimanager.widget.dialog.TipsDialog;

/**
 * @author Administrator
 */
public class WifiToolkitActivity extends BaseActivity<NodeOptionSetContract.Presenter, NodeWifiListBean> implements NodeOptionSetContract.View {

    @BindView(R2.id.tv_has_no_password)
    TextView tvHasNoPassword;
    @BindView(R2.id.tv_setting_wifi_name)
    TextView tvSettingWifiName;
    @BindView(R2.id.tv_wifi_ssid)
    TextView tvWifiSSID;
    @BindView(R2.id.tv_setting_wifi_password)
    TextView tvSettingWifiPassword;
    //    @BindView(R2.id.tv_guard_join_net)
//    TextView tvGuardJoinNet;
//    @BindView(R2.id.tv_senior_settings)
//    TextView tvSeniorSettings;
    @BindView(R2.id.tv_online_setting)
    TextView tvOnlineSetting;
    //    @BindView(R2.id.tv_lan_setting)
//    TextView tvLanSetting;
    @BindView(R2.id.tv_restart_device)
    TextView tvRestartDevice;
    //    @BindView(R2.id.tv_dhcp_server)
//    TextView tvDhcpServer;
    @BindView(R2.id.tv_reset_device)
    TextView tvResetDevice;
    @BindView(R2.id.gl_senior_setting_wrapper)
    GridLayout glSeniorSettingWrapper;
    private TInputDialog nameModifyDialog;
    private TInputDialog passwordModifyDialog;
    private TipsDialog restartTipsDialog;
    private TipsDialog resetTipsDialog;
    private WiFiSettingProxyModel proxyModel;
    private List<NodeWifiListBean.WifiBean> wifiList;

    @Override
    public int layoutId() {
        return R.layout.activity_wifi_toolkit;
    }

    @Override
    public NodeOptionSetContract.Presenter getPresenter() {
        return new NodeOptionSetPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.wifi_toolkit);
        proxyModel = new WiFiSettingProxyModel();
        glSeniorSettingWrapper.setColumnCount(DensityUtil.getScreenWidth() < 728 ? 3 : 4);
        mPresenter.getNodeSsid(MyApplication.getAppContext().getCurrentSelectNode());
        if (NetWorkUtils.isCurrentXiaoK(MyApplication.getAppContext().getCurrentSelectNode())) {
            tvWifiSSID.setText(NetWorkUtils.getRealSSIDWhenWifi(this));
        }
    }

    @OnClick(R2.id.tv_setting_wifi_name)
    public void onTvSettingWifiNameClicked() {
        showNameInputDialog();
    }

    @OnClick(R2.id.tv_setting_wifi_password)
    public void onTvSettingWifiPasswordClicked() {
        showPasswordInputDialog();
    }

    @OnClick(R2.id.tv_guard_join_net)
    public void onTvGuardJoinNetClicked() {
        startActivity(GuardDeviceJoinActivity.class);
    }

//    @OnClick(R2.id.tv_senior_settings)
//    public void onTvSeniorSettingsClicked() {
//        startActivity(SeniorWifiSettingActivity.class);
//    }

    @OnClick(R2.id.tv_online_setting)
    public void onTvOnlineSettingClicked() {
        startActivity(OnlineSettingActivity.class);
    }

//    @OnClick(R2.id.tv_lan_setting)
//    public void onTvLanSettingClicked() {
//        startActivity(LanSettingActivity.class);
//    }

    @OnClick(R2.id.tv_restart_device)
    public void onTvRestartDeviceClicked() {
        showRestartTipDialog();
    }

//    @OnClick(R2.id.tv_dhcp_server)
//    public void onTvDhcpServerClicked() {
//        startActivity(DHCPServerActivity.class);
//    }

    @OnClick(R2.id.tv_reset_device)
    public void onTvResetDeviceClicked() {
        showResetTipDialog();
    }

    @Override
    public void onLoadData(NodeWifiListBean resultData) {
        if (resultData == null) {
            return;
        }
        wifiList = resultData.getPage();
        if (Check.hasContent(wifiList)) {
            NodeWifiListBean.WifiBean wifiBean = wifiList.get(0);
            tvWifiSSID.setText(wifiBean.getSsid());
            tvHasNoPassword.setText(Check.hasContent(wifiBean.getPasswd()) ? R.string.has_set_password : R.string.no_password_easy_gay);
        }
    }

    private void showNameInputDialog() {
        if (nameModifyDialog == null) {
            nameModifyDialog = new TInputDialog(this);
            nameModifyDialog.setTitle(R.string.set_wifi_name);
            nameModifyDialog.setEditHint(R.string.input_new_name_please);
            nameModifyDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    dismiss(nameModifyDialog);
                }

                @Override
                public void onRightClick(String s) {
                    if (NetWorkUtils.isCurrentXiaoK(MyApplication.getAppContext().getCurrentSelectNode())) {
                        modifyNameLocal(s, "");
                    } else {
                        mPresenter.modifySsid(WiFiHttpClient.getWifiDeviceInfo().getId(), Values.FREQ_ALL, s);
                    }
                }
            });
        }
        nameModifyDialog.clearInputText();
        nameModifyDialog.show();
    }

    private void modifyNameLocal(String name, String passwd) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(Keys.SSID0, NetWorkUtils.getRealSSIDWhenWifi(MyApplication.getAppContext()));
        params.put(Keys.SSID, name);
        if (Check.hasContent(passwd)) {
//            params.put(Keys.PASSWD_WIFI, SecurityUtils.md5(passwd));
            params.put(Keys.PASSWD_WIFI, passwd);
        }
        proxyModel.modifyWifiInfo(RequestBodyUtils.convert(params), new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                tvWifiSSID.setText(name);
                dismiss(nameModifyDialog, passwordModifyDialog);
                ToastUtils.showShort(R.string.option_success_restart);
            }

            @Override
            public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                dismiss(nameModifyDialog, passwordModifyDialog);
                ToastUtils.showShort(R.string.option_failed_retry);
                WiFiHttpClient.dealWithResultCode(resultCode);
            }
        });
    }

    private void showPasswordInputDialog() {
        if (passwordModifyDialog == null) {
            passwordModifyDialog = new TInputDialog(this);
            passwordModifyDialog.setTitle(R.string.set_wifi_passowrd);
            passwordModifyDialog.setEditHint(R.string.input_wifi_passowrd_please);
            passwordModifyDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    dismiss(nameModifyDialog, passwordModifyDialog);
                }

                @Override
                public void onRightClick(String s) {
                    if (NetWorkUtils.isXiaoKSignIn()) {
                        modifyNameLocal(NetWorkUtils.getRealSSIDWhenWifi(MyApplication.getAppContext()), s);
                    } else {
                        mPresenter.modifyPasswd(WiFiHttpClient.getWifiDeviceInfo().getId(), Values.FREQ_ALL, s);
                    }
                }
            });
        }
        passwordModifyDialog.clearInputText();
        passwordModifyDialog.show();
    }


    private void showRestartTipDialog() {
        if (restartTipsDialog == null) {
            restartTipsDialog = new TipsDialog(this)
                    .icon(R.mipmap.ic_test_logo)
                    .title(R.string.confirm_restart_please)
                    .content(R.string.tips_device_restart)
                    .doClick(new TipsDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(TextView tvLeft) {
                            restartTipsDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(TextView tvRight) {
                            restart();
                        }
                    });
        }
        restartTipsDialog.show();
    }

    private void restart() {
        TLog.i("OkHttp restart---");
        proxyModel.restart(new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                dismiss(restartTipsDialog);
                ToastUtils.showShort(R.string.wifi_restart_option_success);
            }

            @Override
            public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                dismiss(restartTipsDialog);
                ToastUtils.showShort(R.string.wifi_restart_failed);
                WiFiHttpClient.dealWithResultCode(resultCode);
            }
        });
    }

    private void reset() {
        proxyModel.reset(new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                dismiss(resetTipsDialog);
                ToastUtils.showShort(R.string.reset_device_success);
            }

            @Override
            public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                dismiss(resetTipsDialog);
                ToastUtils.showShort("恢复出厂设置失败");
                WiFiHttpClient.dealWithResultCode(resultCode);
            }
        });
    }


    private void showResetTipDialog() {
        if (resetTipsDialog == null) {
            resetTipsDialog = new TipsDialog(this).icon(R.mipmap.ic_test_logo)
                    .title(R.string.confirm_reset_please)
                    .content(R.string.tips_device_reset)
                    .doClick(new TipsDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(TextView tvLeft) {
                            dismiss(resetTipsDialog);
                        }

                        @Override
                        public void onClickRight(TextView tvRight) {
                            reset();
                        }
                    });
        }
        resetTipsDialog.show();
    }

    @Override
    public void onSSIDResponseOK() {
        dismiss(nameModifyDialog, passwordModifyDialog);
        ToastUtils.showShort(R.string.modify_success);
    }

    @Override
    public void onPwdResponseOK() {
        dismiss(nameModifyDialog, passwordModifyDialog);
        ToastUtils.showShort(R.string.modify_success);
    }

    @Override
    protected void onDestroy() {
        dismiss(nameModifyDialog, passwordModifyDialog, resetTipsDialog, restartTipsDialog);
        super.onDestroy();
    }
}
