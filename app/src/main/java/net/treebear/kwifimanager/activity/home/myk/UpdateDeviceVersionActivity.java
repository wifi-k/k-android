package net.treebear.kwifimanager.activity.home.myk;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.DeviceBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class UpdateDeviceVersionActivity extends BaseActivity {

    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_serial)
    TextView tvDeviceSerial;
    @BindView(R.id.tv_online)
    TextView tvOnline;
    @BindView(R.id.tv_device_current_version)
    TextView tvDeviceCurrentVersion;
    @BindView(R.id.tv_newer_version)
    TextView tvNewerVersion;
    private DeviceBean deviceInfo;

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            deviceInfo = (DeviceBean) params.getSerializable(Keys.DEVICE_INFO);
        }
    }

    @Override
    public int layoutId() {
        return R.layout.activity_update_device_version;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.update_version);
        if (deviceInfo != null) {
            tvDeviceName.setText(deviceInfo.getName());
            tvDeviceCurrentVersion.setText(deviceInfo.getVersion());
            tvNewerVersion.setText(deviceInfo.getUpdateVersion());
            tvDeviceSerial.setText(deviceInfo.getSerialId());
            tvOnline.setText(deviceInfo.isOnline() ? R.string.online : R.string.offline);
            tvOnline.setTextColor(deviceInfo.isOnline() ? Color.WHITE : Config.Colors.DEVICE_K_OFFLINE);
            tvOnline.setBackgroundResource(deviceInfo.isOnline() ? R.drawable.btn_green_to_cyan_r4 : R.drawable.bg_f7_r4);
        }
    }

    @OnClick(R.id.tv_update_now)
    public void onViewClicked() {
        // TODO: 2019/3/11 这里弹个窗更新吗？
    }
}
