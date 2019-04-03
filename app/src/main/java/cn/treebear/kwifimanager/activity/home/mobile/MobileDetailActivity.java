package cn.treebear.kwifimanager.activity.home.mobile;

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
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseSeekBarChangeListener;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import cn.treebear.kwifimanager.mvp.server.presenter.AllMobileListPresenter;
import cn.treebear.kwifimanager.util.DateTimeUtils;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

/**
 * @author Administrator
 */
public class MobileDetailActivity extends BaseActivity<AllMobileListContract.Presenter, MobileListBean> implements AllMobileListContract.View {

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
        }
    }

    @Override
    protected void initView() {
        statusTransparentFontWhite();
        setTitle(R.mipmap.ic_line_arrow_left_white, getString(R.string.device_detail), "", 0, false);
        tvDeviceName.setText(mobilePhoneBean.getName());
        boolean isOnline = mobilePhoneBean.getStatus() == 1;
        tvDeviceTime.setText(String.format("%s " + (isOnline ? "上线" : "离线"),
                DateTimeUtils.formatMDHmm(isOnline ? mobilePhoneBean.getOnTime() : mobilePhoneBean.getOffTime())));
//        swbOnlineChildren.setChecked(mobilePhoneBean.isChildren());
//        swbOnlineAlarm.setChecked(mobilePhoneBean.isOnlineAlarm());
        GlideApp.with(this).load(mobilePhoneBean.getMacIcon())
                .placeholder(R.mipmap.ic_device_pad).error(R.mipmap.ic_device_pad).into(ivDeviceType);
        swbBlacklisting.setChecked(mobilePhoneBean.getIsBlock() == 1);
        swbSpeedLimit.setChecked(false);
        sbDownloadSpeed.setEnabled(false);
        sbUploadSpeed.setEnabled(false);

        tvUploadSpeed.setText(true ? String.format("%sMB/S", sbUploadSpeed.getProgress() / 10d) : "不限速");
        tvDownloadSpeed.setText(true ? String.format("%sMB/S", sbDownloadSpeed.getProgress() / 10d) : "不限速");
        sbDownloadSpeed.setEnabled(false);
        sbUploadSpeed.setEnabled(false);
        swbOnlineChildren.setOnCheckedChangeListener((view, isChecked) -> {
        });
        swbOnlineAlarm.setOnCheckedChangeListener((view, isChecked) -> {
        });
        swbBlacklisting.setOnCheckedChangeListener((view, isChecked) ->
                mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(),
                        mobilePhoneBean.getMac(), mobilePhoneBean.getNote(), isChecked ? 1 : 0));
        swbSpeedLimit.setOnCheckedChangeListener((view, isChecked) -> {
//            mobilePhoneBean.setLimitSpeed(isChecked);
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
                    modifyNameDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    mobilePhoneBean.setNote(s);
                    mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(),
                            mobilePhoneBean.getMac(), s, mobilePhoneBean.getIsBlock());
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
    public void onModifyMobileInfoResponse(BaseResponse response) {
        if (response != null && response.getCode() == 0) {
            modifyNameDialog.dismiss();
            mobilePhoneBean.setName(mobilePhoneBean.getNote());
            tvDeviceName.setText(mobilePhoneBean.getNote());
        } else {
            ToastUtils.showShort(R.string.modify_failed);
        }
    }
}
