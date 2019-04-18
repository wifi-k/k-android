package cn.treebear.kwifimanager.activity.home.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseSeekBarChangeListener;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import cn.treebear.kwifimanager.mvp.server.presenter.AllMobileListPresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DateTimeUtils;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

/**
 * @author Administrator
 */
public class MobileDetailActivity extends BaseActivity<AllMobileListContract.Presenter, MobileListBean> implements AllMobileListContract.View {

    @BindView(R2.id.iv_device_type)
    ImageView ivDeviceType;
    @BindView(R2.id.ll_user_name_wrapper)
    LinearLayout llUserNameWrapper;
    @BindView(R2.id.sw_online_alarm)
    SwitchButton swbOnlineAlarm;
    @BindView(R2.id.swb_blacklisting)
    SwitchButton swbBlacklisting;
    @BindView(R2.id.tv_download_speed)
    TextView tvDownloadSpeed;
    @BindView(R2.id.swb_speed_limit)
    SwitchButton swbSpeedLimit;
    @BindView(R2.id.sb_upload_speed)
    SeekBar sbUploadSpeed;
    @BindView(R2.id.tv_upload_speed)
    TextView tvUploadSpeed;
    @BindView(R2.id.sb_download_speed)
    SeekBar sbDownloadSpeed;
    @BindView(R2.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R2.id.tv_device_time)
    TextView tvDeviceTime;
    @BindView(R2.id.tv_device_status)
    TextView tvDeviceStatus;
    @BindView(R2.id.sw_online_children)
    SwitchButton swbOnlineChildren;
    int count = 0;
    private TInputDialog modifyNameDialog;
    private MobileListBean.MobileBean mobilePhoneBean;

    @Override
    public int layoutId() {
        return R.layout.activity_device_detail;
    }

    @Override
    public AllMobileListContract.Presenter getPresenter() {
        return new AllMobileListPresenter();
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            mobilePhoneBean = (MobileListBean.MobileBean) params.getSerializable(Keys.MOBILE);
            TLog.i(mobilePhoneBean);
        }
    }

    @Override
    protected void initView() {
        statusTransparentFontWhite();
        setTitle(R.mipmap.ic_line_arrow_left_white, getString(R.string.device_detail), "", 0, false);
        updateView();
        setListener();
        tvUploadSpeed.setText("不限速");
        tvDownloadSpeed.setText("不限速");
    }

    private void setListener() {
        swbOnlineChildren.setOnCheckedChangeListener((view, isChecked) -> mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(),
                mobilePhoneBean.getMac(), mobilePhoneBean.getNote(), swbBlacklisting.isChecked() ? 1 : 0,
                isChecked ? 1 : 0, swbOnlineAlarm.isChecked() ? 1 : 0));
        swbOnlineAlarm.setOnCheckedChangeListener((view, isChecked) -> mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(),
                mobilePhoneBean.getMac(), mobilePhoneBean.getNote(), swbBlacklisting.isChecked() ? 1 : 0,
                swbOnlineChildren.isChecked() ? 1 : 0, isChecked ? 1 : 0));
        swbBlacklisting.setOnCheckedChangeListener((view, isChecked) ->
                mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(),
                        mobilePhoneBean.getMac(), mobilePhoneBean.getNote(), isChecked ? 1 : 0,
                        swbOnlineChildren.isChecked() ? 1 : 0, swbOnlineAlarm.isChecked() ? 1 : 0));
        swbSpeedLimit.setOnCheckedChangeListener((view, isChecked) -> {
            if (++count % 2 == 1) {
                ToastUtils.showShort("功能正在开发，敬请期待");
            }
//            mobilePhoneBean.setLimitSpeed(isChecked);
//            sbDownloadSpeed.setEnabled(isChecked);
//            sbUploadSpeed.setEnabled(isChecked);
//            tvUploadSpeed.setText(isChecked ? String.format("%sMB/S", sbUploadSpeed.getProgress() / 10d) : "不限速");
//            tvDownloadSpeed.setText(isChecked ? String.format("%sMB/S", sbDownloadSpeed.getProgress() / 10d) : "不限速");
        });
        sbUploadSpeed.setOnSeekBarChangeListener(new BaseSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (swbSpeedLimit.isChecked()) {
                    tvUploadSpeed.setText(String.format("%sMB/S", progress / 10d));
                }
            }
        });
        sbDownloadSpeed.setOnSeekBarChangeListener(new BaseSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (swbSpeedLimit.isChecked()) {
                    tvDownloadSpeed.setText(String.format("%sMB/S", progress / 10d));
                }
            }
        });
    }

    @OnClick(R2.id.iv_modify_device_name)
    public void onmodifyDeviceNameClicked() {
        showModifyNameDialog();
    }

    private void updateView() {
        if (mobilePhoneBean == null) {
            return;
        }
        tvDeviceName.setText(Check.hasContent(mobilePhoneBean.getNote()) ? mobilePhoneBean.getNote() : mobilePhoneBean.getName());
        boolean isOnline = mobilePhoneBean.getStatus() == 1;
        tvDeviceStatus.setText(isOnline ? R.string._online : R.string._offline);
        tvDeviceTime.setText(DateTimeUtils.formatMDHmm(isOnline ? mobilePhoneBean.getOnTime() : mobilePhoneBean.getOffTime()).replace(" ", ""));
        swbOnlineChildren.setChecked(mobilePhoneBean.getIsRecord() == 1);
        swbOnlineAlarm.setChecked(mobilePhoneBean.getIsOnline() == 1);
        GlideApp.with(this).load(mobilePhoneBean.getMacIcon())
                .placeholder(R.mipmap.ic_device_pad).error(R.mipmap.ic_device_pad).into(ivDeviceType);
        swbBlacklisting.setChecked(mobilePhoneBean.getIsBlock() == 1);
        swbSpeedLimit.setChecked(false);
        sbDownloadSpeed.setEnabled(false);
        sbUploadSpeed.setEnabled(false);
        tvUploadSpeed.setText(String.format("%sMB/S", sbUploadSpeed.getProgress() / 10d));
        tvDownloadSpeed.setText(String.format("%sMB/S", sbDownloadSpeed.getProgress() / 10d));
        sbDownloadSpeed.setEnabled(false);
        sbUploadSpeed.setEnabled(false);
    }

    @OnClick(R2.id.tv_device_info)
    public void onTvDeviceInfoClicked() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Keys.MOBILE, mobilePhoneBean);
        startActivity(MobileInfoActivity.class, bundle);
    }

    private void showModifyNameDialog() {
        if (modifyNameDialog == null) {
            modifyNameDialog = new TInputDialog(this);
            modifyNameDialog.setTitle(R.string.remark_name);
            modifyNameDialog.setEditHint(R.string.input_name_please);
            modifyNameDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    dismiss(modifyNameDialog);
                }

                @Override
                public void onRightClick(String s) {
                    if (Check.hasContent(s)) {
                        mobilePhoneBean.setNote(s);
                        mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(),
                                mobilePhoneBean.getMac(), s, mobilePhoneBean.getIsBlock(),
                                swbOnlineChildren.isChecked() ? 1 : 0, swbOnlineAlarm.isChecked() ? 1 : 0);
                    } else {
                        ToastUtils.showShort(R.string.device_name_cannot_empty);
                    }
                }
            });
        }
        modifyNameDialog.clearInputText();
        modifyNameDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismiss(modifyNameDialog);
        super.onDestroy();
    }

    @Override
    protected void onTitleLeftClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Keys.MOBILE, mobilePhoneBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        super.onTitleLeftClick();
    }

    @Override
    public void onModifyMobileInfoResponse(BaseResponse response) {
        if (response != null && response.getCode() == 0) {
            dismiss(modifyNameDialog);
            ToastUtils.showShort(R.string.set_option_success);
            mobilePhoneBean.setName(Check.hasContent(mobilePhoneBean.getNote()) ? mobilePhoneBean.getNote() : mobilePhoneBean.getName());
            mobilePhoneBean.setIsBlock(swbBlacklisting.isChecked() ? 1 : 0);
            mobilePhoneBean.setIsRecord(swbOnlineChildren.isChecked() ? 1 : 0);
            mobilePhoneBean.setIsOnline(swbOnlineAlarm.isChecked() ? 1 : 0);
            tvDeviceName.setText(mobilePhoneBean.getName());
        } else {
            ToastUtils.showShort(R.string.modify_failed);
        }
    }
}
