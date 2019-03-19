package net.treebear.kwifimanager.fragment;

import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;
import net.treebear.kwifimanager.mvp.wifi.presenter.DynamicIpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class DynamicOnlineFragment extends BaseFragment<DynamicIpContract.IDynamicIpPresenter, WifiDeviceInfo> implements DynamicIpContract.IDynamicIpView {
    @BindView(R.id.tv_connect_status)
    TextView tvConnectStatus;
    @BindView(R.id.tv_ip)
    TextView tvIp;
    @BindView(R.id.tv_subnet)
    TextView tvSubnet;
    @BindView(R.id.tv_gateway)
    TextView tvGateway;
    @BindView(R.id.tv_dns1)
    TextView tvDns1;
    @BindView(R.id.tv_dns2)
    TextView tvDns2;
    int count = 0;

    @Override
    public int layoutId() {
        return R.layout.fragment_dynamic_ip;
    }

    @Override
    public DynamicIpContract.IDynamicIpPresenter getPresenter() {
        return new DynamicIpPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWifiInfoShow();
    }

    @Override
    public void onLoadData(WifiDeviceInfo resultData) {
        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
        deviceInfo.setConnect(true);
        deviceInfo.setWan(resultData.getWan());
        MyApplication.getAppContext().saveDeviceInfo(deviceInfo);
        hideLoading();
        ToastUtils.showShort(R.string.option_update_success);
        updateWifiInfoShow();
    }

    @OnClick(R.id.tv_update_ip_address)
    public void onViewClicked() {
        showLoading();
        mPresenter.dynamicIpSet();
    }

    @Override
    public void onLoadFail(String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                }
                // 延时2秒再次查询
                tvIp.postDelayed(() -> {
                    if (mPresenter != null) {
                        mPresenter.queryNetStatus();
                    }
                }, 2000);
                break;
            case Config.ServerResponseCode.CUSTOM_ERROR:
                hideLoading();
                ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                break;
            default:
                hideLoading();
                ToastUtils.showShort(R.string.connect_fail);
                break;
        }
    }

    private void updateWifiInfoShow() {
        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
        tvConnectStatus.setText(deviceInfo.isConnect() ? R.string.wan_connect_ok : R.string.wan_connect_no);
        tvIp.setText(deviceInfo.getWan().getIp());
        tvSubnet.setText(deviceInfo.getWan().getNetmask());
        tvGateway.setText(deviceInfo.getWan().getGateway());
        tvDns1.setText(deviceInfo.getWan().getDns1());
        tvDns1.setText(deviceInfo.getWan().getDns2());
    }
}
