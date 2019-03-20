package net.treebear.kwifimanager.activity.home.device;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.suke.widget.SwitchButton;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseSeekBarChangeListener;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.test.BeanTest;
import net.treebear.kwifimanager.util.DateTimeUtils;
import net.treebear.kwifimanager.widget.TInputDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class DeviceDetailActivity extends BaseActivity {

    @BindView(R.id.iv_device_type)
    ImageView ivDeviceType;
    @BindView(R.id.ll_user_name_wrapper)
    LinearLayout llUserNameWrapper;
    @BindView(R.id.sw_online_alarm)
    SwitchButton swbOnlineAlarm;
    @BindView(R.id.swb_blacklisting)
    SwitchButton swbBlacklisting;
    @BindView(R.id.tv_download_speed)
    TextView tvDownloadSpeed;
    @BindView(R.id.swb_speed_limit)
    SwitchButton swbSpeedLimit;
    @BindView(R.id.sb_upload_speed)
    SeekBar sbUploadSpeed;
    @BindView(R.id.tv_upload_speed)
    TextView tvUploadSpeed;
    @BindView(R.id.sb_download_speed)
    SeekBar sbDownloadSpeed;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_time)
    TextView tvDeviceTime;
    @BindView(R.id.sw_online_children)
    SwitchButton swbOnlineChildren;
    private int position;
    private MobilePhoneBean mobilePhoneBean;
    private TInputDialog modifyNameDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_device_detail;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            position = params.getInt(Keys.POSITION, 0);
        }
    }

    @Override
    protected void initView() {
        statusTransparentFontWhite();
        setTitle(R.mipmap.back, getString(R.string.device_detail), "", 0, false);
        mobilePhoneBean = BeanTest.getMobilePhoneList(10).get(position);
        tvDeviceName.setText(mobilePhoneBean.getName());
        tvDeviceTime.setText(String.format("%s " + (mobilePhoneBean.isOnline() ? "上线" : "离线"),
                DateTimeUtils.formatMDHmm(mobilePhoneBean.isOnline() ? mobilePhoneBean.getOnlineTime() : mobilePhoneBean.getOfflineTime())));
        swbOnlineChildren.setChecked(mobilePhoneBean.isChildren());
        swbOnlineAlarm.setChecked(mobilePhoneBean.isOnlineAlarm());
        swbBlacklisting.setChecked(mobilePhoneBean.isBanOnline());
        swbSpeedLimit.setChecked(mobilePhoneBean.isLimitSpeed());
        sbDownloadSpeed.setEnabled(!mobilePhoneBean.isLimitSpeed());
        sbUploadSpeed.setEnabled(!mobilePhoneBean.isLimitSpeed());

        tvUploadSpeed.setText(mobilePhoneBean.isLimitSpeed() ? String.format("%sMB/S", sbUploadSpeed.getProgress() / 10d) : "不限速");
        tvDownloadSpeed.setText(mobilePhoneBean.isLimitSpeed() ? String.format("%sMB/S", sbDownloadSpeed.getProgress() / 10d) : "不限速");
        sbDownloadSpeed.setEnabled(mobilePhoneBean.isLimitSpeed());
        sbUploadSpeed.setEnabled(mobilePhoneBean.isLimitSpeed());
        swbOnlineChildren.setOnCheckedChangeListener((view, isChecked) -> BeanTest.getMobilePhoneList(10).get(position).setChildren(isChecked));
        swbOnlineAlarm.setOnCheckedChangeListener((view, isChecked) -> BeanTest.getMobilePhoneList(10).get(position).setOnlineAlarm(isChecked));
        swbBlacklisting.setOnCheckedChangeListener((view, isChecked) -> BeanTest.getMobilePhoneList(10).get(position).setBanOnline(isChecked));
        swbSpeedLimit.setOnCheckedChangeListener((view, isChecked) -> {
            BeanTest.getMobilePhoneList(10).get(position).setLimitSpeed(isChecked);
            mobilePhoneBean.setLimitSpeed(isChecked);
            sbDownloadSpeed.setEnabled(isChecked);
            sbUploadSpeed.setEnabled(isChecked);
            tvUploadSpeed.setText(isChecked ? String.format("%sMB/S", sbUploadSpeed.getProgress() / 10d) : "不限速");
            tvDownloadSpeed.setText(isChecked ? String.format("%sMB/S", sbDownloadSpeed.getProgress() / 10d) : "不限速");
        });
        sbUploadSpeed.setOnSeekBarChangeListener(new BaseSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvUploadSpeed.setText(String.format("%sMB/S", progress / 10d));
            }
        });
        sbDownloadSpeed.setOnSeekBarChangeListener(new BaseSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDownloadSpeed.setText(String.format("%sMB/S", progress / 10d));
            }
        });
    }

    @OnClick(R.id.iv_modify_device_name)
    public void onmodifyDeviceNameClicked() {
        showModifyNameDialog();
    }

    @OnClick(R.id.tv_device_info)
    public void onTvDeviceInfoClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.POSITION, position);
        startActivity(DeviceInfoActivity.class, bundle);
    }

    private void showModifyNameDialog() {
        if (modifyNameDialog == null) {
            modifyNameDialog = new TInputDialog(this);
            modifyNameDialog.setTitle(R.string.remark_name);
            modifyNameDialog.setEditHint(R.string.input_name_please);
            modifyNameDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    modifyNameDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/3/13 修改名称
                    modifyNameDialog.dismiss();
                    mobilePhoneBean.setName(s);
                    tvDeviceName.setText(s);
                    BeanTest.getMobilePhoneList(10).get(position).setName(s);
                }
            });
        }
        modifyNameDialog.clearInputText();
        modifyNameDialog.show();
    }
}
