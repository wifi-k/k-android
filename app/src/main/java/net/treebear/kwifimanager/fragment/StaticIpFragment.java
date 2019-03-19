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
import net.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;
import net.treebear.kwifimanager.mvp.wifi.presenter.StaticIpPresenter;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class StaticIpFragment extends BaseFragment<StaticIpContract.IStaticIpPresenter,WifiDeviceInfo> implements StaticIpContract.IStaticIpView {
    @BindView(R.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R.id.et_subnet)
    EditText etSubnet;
    @BindView(R.id.et_gateway)
    EditText etGateway;
    @BindView(R.id.et_dns_1)
    EditText etDns1;
    @BindView(R.id.et_dns_2)
    EditText etDns2;
    @BindView(R.id.tv_update_option)
    TextView tvUpdateOption;
    private int count;

    @Override
    public int layoutId() {
        return R.layout.fragment_static_ip;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWifiInfoShow();
    }

    @Override
    public StaticIpContract.IStaticIpPresenter getPresenter() {
        return new StaticIpPresenter();
    }

    @OnClick(R.id.tv_update_option)
    public void onTvUpdateOptionClicked() {
        if (checkInput()) {
            showLoading();
            count = 0;
            mPresenter.staticIpSet(
                    etIpAddress.getText().toString(),
                    etSubnet.getText().toString(),
                    etGateway.getText().toString(),
                    etDns1.getText().toString(),
                    etDns2.getText().toString()
            );
        }
    }

    @Override
    public void onLoadData(WifiDeviceInfo resultData) {
        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
        deviceInfo.setConnect(true);
        deviceInfo.setWan(resultData.getWan());
        MyApplication.getAppContext().saveDeviceInfo(deviceInfo);
        hideLoading();
        startActivity(ModifyWifiInfoActivity.class);
        ToastUtils.showShort(R.string.connect_success);
    }

    @Override
    public void onLoadFail(String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                }
                tvUpdateOption.postDelayed(() -> {
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

    /**
     * 初步检查输入正确性
     */
    private boolean checkInput() {
        if (!Check.ipLike(etIpAddress.getText().toString())) {
            ToastUtils.showShort(R.string.ip_address_error);
            return false;
        }
        if (!Check.ipLike(etSubnet.getText().toString())) {
            ToastUtils.showShort(R.string.subnet_mask_error);
            return false;
        }
        if (!Check.ipLike(etGateway.getText().toString())) {
            ToastUtils.showShort(R.string.gateway_error);
            return false;
        }
        if (!Check.ipLike(etDns1.getText().toString())) {
            ToastUtils.showShort(R.string.first_dns_server_error);
            return false;
        }
//        if (!Check.ipLike(etSecendDns.getText().toString())) {
//            ToastUtils.showShort(R.string.second_dns_server_error);
//            return false;
//        }
        return true;
    }

    private void updateWifiInfoShow() {
        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
        etIpAddress.setText(deviceInfo.getWan().getIp());
        etSubnet.setText(deviceInfo.getWan().getNetmask());
        etGateway.setText(deviceInfo.getWan().getGateway());
        etDns1.setText(deviceInfo.getWan().getDns1());
        etDns2.setText(deviceInfo.getWan().getDns2());
    }

}
