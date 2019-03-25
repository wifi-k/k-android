package net.treebear.kwifimanager.fragment;

import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.home.settings.ModifyWifiInfoActivity;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.wifi.contract.DialUpContract;
import net.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class PPPOEFragment extends BaseFragment<DialUpContract.IDialUpPresenter, WifiDeviceInfo> implements DynamicIpContract.IDynamicIpView {
    @BindView(R.id.tv_broadband_account)
    EditText etBroadbandAccount;
    @BindView(R.id.et_broadband_password)
    EditText etBroadbandPassword;
    @BindView(R.id.ip_address)
    TextView tvIpAddress;
    @BindView(R.id.tv_dns_server)
    TextView tvDnsServer;
    @BindView(R.id.tv_disconnect)
    TextView tvDisconnect;
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    private int count = 0;

    @Override
    public int layoutId() {
        return R.layout.fragment_pppoe;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWifiInfoShow();
    }

    @OnClick(R.id.tv_disconnect)
    public void onTvDisconnectClicked() {
        showLoading(R.string.commit_ing);
        mPresenter.dialUpSet(Config.Text.AP_NAME_START + etBroadbandAccount.getText().toString(),
                Config.Text.AP_NAME_START + etBroadbandPassword.getText().toString());
    }

    @OnClick(R.id.tv_connect)
    public void onTvConnectClicked() {
        if (etBroadbandAccount.getText().length() < 1 || etBroadbandPassword.getText().length() < 1) {
            ToastUtils.showShort(R.string.check_pppoe_account_info);
            return;
        }
        count = 0;
        mPresenter.dialUpSet(etBroadbandAccount.getText().toString(), etBroadbandPassword.getText().toString());
        showLoading();
    }

    @Override
    public void onLoadData(WifiDeviceInfo resultData) {
        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
        deviceInfo.setConnect(true);
        deviceInfo.setWan(resultData.getWan());
        MyApplication.getAppContext().saveDeviceInfo(deviceInfo);
        hideLoading();
        startActivity(ModifyWifiInfoActivity.class);
    }

    @Override
    public void onLoadFail(String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                }
                tvDnsServer.postDelayed(() -> {
                    if (mPresenter != null) {
                        mPresenter.queryNetStatus();
                    }
                }, 2000);
                break;
            default:
                hideLoading();
                ToastUtils.showShort(R.string.connect_fail);
                break;
        }
    }

    private void updateWifiInfoShow() {
        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
        etBroadbandAccount.setText(deviceInfo.getWan().getName());
        etBroadbandPassword.setText(deviceInfo.getWan().getPasswd());
        tvDnsServer.setText(deviceInfo.getWan().getDns1());
        tvIpAddress.setText(deviceInfo.getWan().getIp());
    }

}
