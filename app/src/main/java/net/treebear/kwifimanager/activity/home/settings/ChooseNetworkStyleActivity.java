package net.treebear.kwifimanager.activity.home.settings;

import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Values;
import net.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;
import net.treebear.kwifimanager.mvp.wifi.presenter.DynamicIpPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.widget.LoadingProgressDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class ChooseNetworkStyleActivity extends BaseActivity<DynamicIpContract.IDynamicIpPresenter, WifiDeviceInfo> implements DynamicIpContract.IDynamicIpView {

    @BindView(R.id.rg_online_type)
    RadioGroup rgOnlineType;

    private int onlineType;

    int count = 0;

    @Override
    public int layoutId() {
        return R.layout.activity_choose_network_style;
    }

    @Override
    public DynamicIpContract.IDynamicIpPresenter getPresenter() {
        return new DynamicIpPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        rgOnlineType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_online_type_dial:
                        onlineType = Values.ONLINE_TYPE_DIAL;
                        break;
                    case R.id.rb_online_type_static:
                        onlineType = Values.ONLINE_TYPE_STATIC_IP;
                        break;
                    default:
                        onlineType = Values.ONLINE_TYPE_DYNAMIC_IP;
                        break;
                }
            }
        });
    }

    @OnClick(R.id.btn_online_type_next)
    public void onViewClicked() {
        switch (onlineType) {
            case Values.ONLINE_TYPE_DIAL:
                startActivity(DialUpOnlineActivity.class);
                break;
            case Values.ONLINE_TYPE_STATIC_IP:
                startActivity(StaticIpOnlineActivity.class);
                break;
            default:
                LoadingProgressDialog.showProgressDialog(this, getString(R.string.try_to_connect_wifi));
                mPresenter.dynamicIpSet();
                count = 0;
                showLoading();
                // TODO: 2019/3/13
//                startActivity(ModifyWifiInfoActivity.class);
                break;
        }
    }

    @Override
    public void onLoadData(WifiDeviceInfo resultData) {
//        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
//        deviceInfo.setConnect(true);
//        deviceInfo.setWan(resultData.getWan());
//        MyApplication.getAppContext().saveDeviceInfo(deviceInfo);
    }

    @Override
    public void onLoadFail(String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                }
                // 延时1秒再次查询
                rgOnlineType.postDelayed(() -> {
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

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        mPresenter = null;
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}
