package net.treebear.kwifimanager.activity.toolkit;

import android.support.v7.widget.GridLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.mvp.wifi.model.WiFiSettingProxyModel;
import net.treebear.kwifimanager.util.DensityUtil;
import net.treebear.kwifimanager.widget.TInputDialog;
import net.treebear.kwifimanager.widget.TipsDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class WifiToolkitActivity extends BaseActivity {

    @BindView(R.id.tv_has_no_password)
    TextView tvHasNoPassword;
    @BindView(R.id.tv_setting_wifi_name)
    TextView tvSettingWifiName;
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


    @Override
    public int layoutId() {
        return R.layout.activity_wifi_toolkit;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.wifi_toolkit);
        proxyModel = new WiFiSettingProxyModel();
        glSeniorSettingWrapper.setColumnCount(DensityUtil.getScreenWidth() < 728 ? 3 : 4);
    }

    @OnClick(R.id.tv_setting_wifi_name)
    public void onTvSettingWifiNameClicked() {
        showNameInputDialog();
    }

    @OnClick(R.id.tv_setting_wifi_password)
    public void onTvSettingWifiPasswordClicked() {
        showPasswordInputDialog();
    }

//    @OnClick(R.id.tv_guard_join_net)
//    public void onTvGuardJoinNetClicked() {
//        startActivity(GuardDeviceJoinActivity.class);
//    }
//
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

    private void showNameInputDialog() {
        if (nameModifyDialog == null) {
            nameModifyDialog = new TInputDialog(this);
            nameModifyDialog.setTitle(R.string.set_wifi_name);
            nameModifyDialog.setEditHint(R.string.input_new_name_please);
            nameModifyDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    nameModifyDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/3/12 修改名称
                    ToastUtils.showShort(s);
                    nameModifyDialog.dismiss();
                }
            });
        }
        nameModifyDialog.clearInputText();
        nameModifyDialog.show();
    }

    private void showPasswordInputDialog() {
        if (passwordModifyDialog == null) {
            passwordModifyDialog = new TInputDialog(this);
            passwordModifyDialog.setTitle(R.string.set_wifi_passowrd);
            passwordModifyDialog.setEditHint(R.string.input_wifi_passowrd_please);
            passwordModifyDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    passwordModifyDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/3/12 修改密码
                    ToastUtils.showShort(s);
                    passwordModifyDialog.dismiss();
                }
            });
        }
        passwordModifyDialog.clearInputText();
        passwordModifyDialog.show();
    }

    private void showRestartTipDialog() {
        if (restartTipsDialog == null) {
            restartTipsDialog = new TipsDialog(this)
                    .icon(R.mipmap.logo)
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
        proxyModel.restart(new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                restartTipsDialog.dismiss();
                ToastUtils.showShort(R.string.wifi_restart_success);
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                restartTipsDialog.dismiss();
                ToastUtils.showShort(R.string.wifi_restart_failed);
            }
        });
    }

    private void reset(){
        proxyModel.reset(new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                resetTipsDialog.dismiss();
                ToastUtils.showShort(R.string.reset_device_success);
            }

            @Override
            public void onFailed(String resultMsg, int resultCode) {
                resetTipsDialog.dismiss();
                ToastUtils.showShort("恢复出厂设置失败");
            }
        });
    }


    private void showResetTipDialog() {
        if (resetTipsDialog == null) {
            resetTipsDialog = new TipsDialog(this).icon(R.mipmap.logo)
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
}
