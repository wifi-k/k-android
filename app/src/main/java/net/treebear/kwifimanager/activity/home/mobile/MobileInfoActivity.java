package net.treebear.kwifimanager.activity.home.mobile;

import android.os.Bundle;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.test.BeanTest;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class MobileInfoActivity extends BaseActivity {

    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_phone_brand)
    TextView tvPhoneBrand;
    @BindView(R.id.tv_mac_address)
    TextView tvMacAddress;
    @BindView(R.id.tv_ip_address)
    TextView tvIpAddress;
    private int position;

    @Override
    public int layoutId() {
        return R.layout.activity_device_info;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            position = params.getInt(Keys.POSITION, 0);
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.device_info);
        MobilePhoneBean mobilePhoneBean = BeanTest.getMobilePhoneList(10).get(position);
        switch (mobilePhoneBean.getType()) {
            case Config.Types
                    .ANDROID:
                tvDeviceType.setText(R.string.android);
                break;
            case Config.Types.APPLE:
                tvDeviceType.setText(R.string.apple);
                break;
            default:
                tvDeviceType.setText(R.string.other);
                break;
        }
        tvPhoneBrand.setText(mobilePhoneBean.getBrand());
        tvMacAddress.setText(mobilePhoneBean.getMac());
        tvIpAddress.setText(mobilePhoneBean.getIp());
    }

}
