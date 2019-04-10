package cn.treebear.kwifimanager.activity.bindap.settings;

import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;
import cn.treebear.kwifimanager.mvp.wifi.presenter.StaticIpPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;

/**
 * @author Administrator
 */
public class StaticIpOnlineActivity extends BaseActivity<StaticIpContract.Presenter, WifiDeviceInfo> implements StaticIpContract.View {

    @BindView(R2.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R2.id.et_subnet_mask)
    EditText etSubnetMask;
    @BindView(R2.id.et_gateway)
    EditText etGateway;
    @BindView(R2.id.et_first_dns)
    EditText etFirstDns;
    @BindView(R2.id.et_secend_dns)
    EditText etSecendDns;
    @BindView(R2.id.btn_next)
    Button btnNext;
    int count = 0;

    @Override
    public int layoutId() {
        return R.layout.activity_static_iponline;
    }

    @Override
    public StaticIpContract.Presenter getPresenter() {
        return new StaticIpPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }

    @OnClick(R2.id.btn_next)
    public void onViewClicked() {
        if (checkInput()) {
            showLoading();
            count = 0;
            mPresenter.staticIpSet(
                    etIpAddress.getText().toString(),
                    etSubnetMask.getText().toString(),
                    etGateway.getText().toString(),
                    etFirstDns.getText().toString(),
                    etSecendDns.getText().toString()
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
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                    return;
                }
                btnNext.postDelayed(() -> {
                    if (mPresenter != null) {
                        mPresenter.queryNetStatus();
                    }
                }, 2000);
                break;
            case Config.WifiResponseCode.CONNECT_SUCCESS:
                hideLoading();
                startActivity(ModifyWifiInfoActivity.class);
                ToastUtils.showShort(R.string.connect_success);
                break;
            default:
                hideLoading();
                ToastUtils.showShort(R.string.connect_fail);
//                ToastUtils.showShort(resultMsg);
                // 延时1秒再次查询
//                btnNext.postDelayed(() -> mPresenter.queryNetStatus(), 1000);
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
        if (!Check.ipLike(etSubnetMask.getText().toString())) {
            ToastUtils.showShort(R.string.subnet_mask_error);
            return false;
        }
        if (!Check.ipLike(etGateway.getText().toString())) {
            ToastUtils.showShort(R.string.gateway_error);
            return false;
        }
        if (!Check.ipLike(etFirstDns.getText().toString())) {
            ToastUtils.showShort(R.string.first_dns_server_error);
            return false;
        }
//        if (!Check.ipLike(etSecendDns.getText().toString())) {
//            ToastUtils.showShort(R.string.second_dns_server_error);
//            return false;
//        }
        return true;
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        mPresenter = null;
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}
