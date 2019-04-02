package cn.treebear.kwifimanager.activity.toolkit;

import android.support.v7.widget.GridLayout;
import android.util.ArrayMap;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.NodeWifiListBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
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

    @BindView(R.id.tv_has_no_password)
    TextView tvHasNoPassword;
    @BindView(R.id.tv_setting_wifi_name)
    TextView tvSettingWifiName;
    @BindView(R.id.tv_wifi_ssid)
    TextView tvWifiSSID;
    @BindView(R.id.tv_setting_wifi_password)
    TextView tvSettingWifiPassword;
    //    @BindView(R.id.tv_guard_join_net)
//    TextView tvGuardJoinNet;
//    @BindView(R.id.tv_senior_settings)
//    TextView tvSeniorSettings;
    @BindView(R.id.tv_online_setting)
    TextView tvOnlineSetting;
    //    @BindView(R.id.tv_lan_setting)
//    TextView tvLanSetting;
    @BindView(R.id.tv_restart_device)
    TextView tvRestartDevice;
    @BindView(R.id.tv_dhcp_server)
    TextView tvDhcpServer;
    @BindView(R.id.tv_reset_device)
    TextView tvResetDevice;
    @BindView(R.id.gl_senior_setting_wrapper)
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
    }

    @OnClick(R.id.tv_setting_wifi_name)
    public void onTvSettingWifiNameClicked() {
        showNameInputDialog();
    }

    @OnClick(R.id.tv_setting_wifi_password)
    public void onTvSettingWifiPasswordClicked() {
        showPasswordInputDialog();
    }

    @OnClick(R.id.tv_guard_join_net)
    public void onTvGuardJoinNetClicked() {
        startActivity(GuardDeviceJoinActivity.class);
    }

//    @OnClick(R.id.tv_senior_settings)
//    public void onTvSeniorSettingsClicked() {
//        startActivity(SeniorWifiSettingActivity.class);
//    }

    @OnClick(R.id.tv_online_setting)
    public void onTvOnlineSettingClicked() {
        startActivity(OnlineSettingActivity.class);
    }

//    @OnClick(R.id.tv_lan_setting)
//    public void onTvLanSettingClicked() {
//        startActivity(LanSettingActivity.class);
//    }

    @OnClick(R.id.tv_restart_device)
    public void onTvRestartDeviceClicked() {
        showRestartTipDialog();
    }

    @OnClick(R.id.tv_dhcp_server)
    public void onTvDhcpServerClicked() {
        startActivity(DHCPServerActivity.class);
    }

    @OnClick(R.id.tv_reset_device)
    public void onTvResetDeviceClicked() {
        showResetTipDialog();
    }

    @Override
    public void onLoadData(NodeWifiListBean resultData) {
        if (resultData == null){
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
                    if (NetWorkUtils.isXiaoKSignIn()) {
                        modifyNameLocal(s, "");
                    } else {
                        mPresenter.modifySsid(MyApplication.getAppContext().getDeviceInfo().getId(), Values.FREQ_ALL, s);
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
        params.put(Keys.PASSWD_WIFI, passwd);
        proxyModel.modifyWifiInfo(RequestBodyUtils.convert(params), new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                ToastUtils.showShort(R.string.option_success_restart);
            }

            @Override
            public void onFailed(BaseResponse response, String resultMsg, int resultCode) {
                ToastUtils.showShort(R.string.option_failed);
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
                    dismiss(passwordModifyDialog);
                }

                @Override
                public void onRightClick(String s) {
                    if (NetWorkUtils.isXiaoKSignIn()) {
                        modifyNameLocal(NetWorkUtils.getRealSSIDWhenWifi(MyApplication.getAppContext()), s);
                    } else {
                        mPresenter.modifyPasswd(MyApplication.getAppContext().getDeviceInfo().getId(), Values.FREQ_ALL, s);
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
                            resetTipsDialog.dismiss();
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
        if (nameModifyDialog != null) {
            nameModifyDialog.dismiss();
            ToastUtils.showShort(R.string.modify_success);
        }
    }

    @Override
    public void onPwdResponseOK() {
        if (passwordModifyDialog != null) {
            passwordModifyDialog.dismiss();
            ToastUtils.showShort(R.string.modify_success);
        }
    }

    @Override
    protected void onDestroy() {
        dismiss(nameModifyDialog, passwordModifyDialog, resetTipsDialog, restartTipsDialog);
        super.onDestroy();
    }
}
