package cn.treebear.kwifimanager.activity.home.mobile;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.Keys;

/**
 * @author Administrator
 */
public class MobileInfoActivity extends BaseActivity {

    @BindView(R2.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R2.id.tv_phone_brand)
    TextView tvPhoneBrand;
    @BindView(R2.id.tv_mac_address)
    TextView tvMacAddress;
    @BindView(R2.id.tv_ip_address)
    TextView tvIpAddress;
    private MobileListBean.MobileBean mobilePhoneBean;

    @Override
    public int layoutId() {
        return R.layout.activity_device_info;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            mobilePhoneBean = ((MobileListBean.MobileBean) params.getSerializable(Keys.MOBILE));
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.device_info);
        String macVendor = mobilePhoneBean.getMacVendor();
        try {
            Integer stringRes = ConstConfig.PHONE_BRAND.get(macVendor.toLowerCase());
            if (stringRes != null && stringRes != 0) {
                tvPhoneBrand.setText(stringRes);
            }else {
                tvPhoneBrand.setText(macVendor);
            }
        } catch (Exception e) {
            tvPhoneBrand.setText(macVendor);
        }
        tvMacAddress.setText(mobilePhoneBean.getMac());
        tvIpAddress.setText(mobilePhoneBean.getLocalIp());
    }

}
