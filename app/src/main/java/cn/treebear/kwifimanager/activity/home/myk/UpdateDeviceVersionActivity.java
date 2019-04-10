package cn.treebear.kwifimanager.activity.home.myk;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.mvp.server.contract.FirmwareUpgradeContract;
import cn.treebear.kwifimanager.mvp.server.presenter.FirmwareUpgradePresenter;
import cn.treebear.kwifimanager.util.Check;

/**
 * @author Administrator
 */
public class UpdateDeviceVersionActivity extends BaseActivity<FirmwareUpgradeContract.Presenter, Object> implements FirmwareUpgradeContract.View {

    @BindView(R2.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R2.id.tv_device_serial)
    TextView tvDeviceSerial;
    @BindView(R2.id.tv_online)
    TextView tvOnline;
    @BindView(R2.id.tv_device_current_version)
    TextView tvDeviceCurrentVersion;
    @BindView(R2.id.tv_newer_version)
    TextView tvNewerVersion;
    private NodeInfoDetail.NodeBean deviceInfo;

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            deviceInfo = (NodeInfoDetail.NodeBean) params.getSerializable(Keys.DEVICE_INFO);
        }
    }

    @Override
    public FirmwareUpgradeContract.Presenter getPresenter() {
        return new FirmwareUpgradePresenter();
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
            tvDeviceCurrentVersion.setText(deviceInfo.getFirmware());
            tvNewerVersion.setText(Check.hasContent(deviceInfo.getFirmwareUpgrade()) ?
                    deviceInfo.getFirmwareUpgrade() : deviceInfo.getFirmware());
            tvDeviceSerial.setText(deviceInfo.getNodeId());
            tvOnline.setText(deviceInfo.getStatus() == 1 ? R.string.online : R.string.offline);
            tvOnline.setTextColor(deviceInfo.getStatus() == 0 ? Color.WHITE : Config.Colors.DEVICE_K_OFFLINE);
            tvOnline.setBackgroundResource(deviceInfo.getStatus() == 0 ? R.drawable.btn_green_to_cyan_r4 : R.drawable.bg_f7_r4);
        }
    }

    @OnClick(R2.id.tv_update_now)
    public void onViewClicked() {
        mPresenter.upgradeNode(deviceInfo.getNodeId());
    }

    @Override
    public void upgradeNodeVersion(int resultCode, String msg) {
        switch (resultCode) {
            case ApiCode.NODE_FIRMWARE_LATEST:
                ToastUtils.showShort(R.string.no_higher_version);
                break;
            case ApiCode.NODE_FIRMWARE_UPGRADING:
                ToastUtils.showShort(R.string.update_ing);
                break;
            default:
                break;
        }
    }
}
