package cn.treebear.kwifimanager.fragment;

import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;
import cn.treebear.kwifimanager.mvp.wifi.presenter.StaticIpPresenter;
import cn.treebear.kwifimanager.util.Check;

/**
 * @author Administrator
 */
public class StaticIpFragment extends BaseFragment<StaticIpContract.Presenter, WifiDeviceInfo> implements StaticIpContract.View {
    @BindView(R2.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R2.id.et_subnet)
    EditText etSubnet;
    @BindView(R2.id.et_gateway)
    EditText etGateway;
    @BindView(R2.id.et_dns_1)
    EditText etDns1;
    @BindView(R2.id.et_dns_2)
    EditText etDns2;
    @BindView(R2.id.tv_update_option)
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
    public StaticIpContract.Presenter getPresenter() {
        return new StaticIpPresenter();
    }

    @OnClick(R2.id.tv_update_option)
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
        WifiDeviceInfo deviceInfo = WiFiHttpClient.getWifiDeviceInfo();
        deviceInfo.setConnect(true);
        deviceInfo.setWan(resultData.getWan());
        WiFiHttpClient.setWifiDeviceInfo(deviceInfo);
        hideLoading();
//        startActivity(ModifyWifiInfoActivity.class);
        ToastUtils.showShort(R.string.option_update_success);
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.connect_fail);
                    return;
                }
                tvUpdateOption.postDelayed(() -> {
                    if (mPresenter != null) {
                        mPresenter.queryNetStatus();
                    }
                }, 2000);
                break;
            case ApiCode.CUSTOM_ERROR:
                hideLoading();
                ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                break;
            case 2:
                WiFiHttpClient.dealWithResultCode(resultCode);
                break;
            default:
                hideLoading();
                ToastUtils.showShort(R.string.connect_fail);
                break;
        }
        WiFiHttpClient.dealWithResultCode(resultCode);
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
        WifiDeviceInfo deviceInfo = WiFiHttpClient.getWifiDeviceInfo();
        etIpAddress.setText(deviceInfo.getWan().getIp());
        etSubnet.setText(deviceInfo.getWan().getNetmask());
        etGateway.setText(deviceInfo.getWan().getGateway());
        etDns1.setText(deviceInfo.getWan().getDns1());
        etDns2.setText(deviceInfo.getWan().getDns2());
    }

}
