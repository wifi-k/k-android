package net.treebear.kwifimanager.activity.home.settings;

import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.wifi.contract.StaticIpContract;
import net.treebear.kwifimanager.mvp.wifi.presenter.StaticIpPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class StaticIpOnlineActivity extends BaseActivity<StaticIpContract.IStaticIpPresenter, Object> implements StaticIpContract.IStaticIpView {

    @BindView(R.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R.id.et_subnet_mask)
    EditText etSubnetMask;
    @BindView(R.id.et_gateway)
    EditText etGateway;
    @BindView(R.id.et_first_dns)
    EditText etFirstDns;
    @BindView(R.id.et_secend_dns)
    EditText etSecendDns;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    public int layoutId() {
        return R.layout.activity_static_iponline;
    }

    @Override
    public StaticIpContract.IStaticIpPresenter getPresenter() {
        return new StaticIpPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        if (checkInput()) {
            showLoading();
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
    public void onLoadFail(String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                hideLoading();
                ToastUtils.showShort(R.string.connect_fail);
                break;
            case Config.WifiResponseCode.CONNECT_SUCCESS:
                hideLoading();
                startActivity(ModifyWifiInfoActivity.class);
                ToastUtils.showShort(R.string.connect_success);
                break;
            default:
                // 延时1秒再次查询
                btnNext.postDelayed(() -> mPresenter.queryNetStatus(), 1000);
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
        if (!Check.ipLike(etSecendDns.getText().toString())) {
            ToastUtils.showShort(R.string.second_dns_server_error);
            return false;
        }
        return true;
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}