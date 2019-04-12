package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.TimeControlbean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.mvp.server.contract.TimeControlContract;
import cn.treebear.kwifimanager.mvp.server.presenter.TimeControlPresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * @author Administrator
 */
public class TimeControlPlanActivity extends BaseActivity<TimeControlContract.Presenter, TimeControlbean> implements TimeControlContract.View {

    @BindView(R2.id.tv_ban_app_name)
    TextView tvBanAppName;
    @BindView(R2.id.tv_ban_app_tips)
    TextView tvBanAppTips;
    @BindView(R2.id.tv_modify_name)
    TextView tvModifyName;
    @BindView(R2.id.tv_limited_online_time)
    TextView tvLimitedTime;
    @BindView(R2.id.tv_has_option_time)
    TextView tvHasOptionTime;
    @BindView(R2.id.tv_has_option_device)
    TextView tvHasOptionDevice;
    private TimeControlbean.TimeControl needModifyPlan;
    private TInputDialog modifyNameDialog;
    private boolean isIncrease;
    private boolean hasModify = false;
    private TMessageDialog unsavedDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_ban_app_plan;
    }

    @Override
    public TimeControlContract.Presenter getPresenter() {
        return new TimeControlPresenter();
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            needModifyPlan = (TimeControlbean.TimeControl) params.getSerializable(Keys.BAN_APP_PLAN);
        }
        TLog.w(needModifyPlan);
        isIncrease = needModifyPlan == null;
        if (needModifyPlan == null) {
            needModifyPlan = new TimeControlbean.TimeControl(0, 1);
        }
        TLog.w(needModifyPlan);
    }

    @Override
    protected void initView() {
        if (!isIncrease) {
            change2ModifyDisplay();
        } else {
            change2IncreaseDisplay();
        }
        tvHasOptionTime.setVisibility(Check.hasContent(needModifyPlan.getSt()) ? View.VISIBLE : View.GONE);
        tvHasOptionDevice.setVisibility(Check.hasContent(needModifyPlan.getsMac()) ? View.VISIBLE : View.GONE);
        tvLimitedTime.setText(R.string.limited_online_time);
        tvBanAppTips.setText(R.string.choose_ban_time_and_device);
    }

    private void change2IncreaseDisplay() {
        setTitleBack(R.string.increase, R.string.save);
        tvBanAppName.setText(R.string.ban_time_plan_name);
    }

    private void change2ModifyDisplay() {
        setTitleBack(R.string.edit, R.string.save);
        tvBanAppName.setText(needModifyPlan.getName());
    }

    @Override
    protected void onTitleRightClick() {
        if (Check.hasContent(needModifyPlan.getSt()) && Check.hasContent(needModifyPlan.getEt()) && needModifyPlan.getWt() > 0) {
            if (Check.hasContent(needModifyPlan.getsMac())) {
                mPresenter.setTimeControlPlan(MyApplication.getAppContext().getCurrentSelectNode(),
                        needModifyPlan.getId(), tvBanAppName.getText().toString(),
                        needModifyPlan.getSt(), needModifyPlan.getEt(), needModifyPlan.getWt(),
                        needModifyPlan.getsMac());
            } else {
                ToastUtils.showShort("请选择需要管理的设备");
            }
        } else {
            ToastUtils.showShort("请选择需要管理的时间段");
        }
    }

    @OnClick(R2.id.tv_modify_name)
    public void onTvModifyNameClicked() {
        showModifyNameDialog();
    }

    @OnClick(R2.id.tv_limited_online_time)
    public void onTvLimitedOnlineTimeClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.START_TIME, needModifyPlan.getSt());
        bundle.putString(Keys.END_TIME, needModifyPlan.getEt());
        bundle.putInt(Keys.WHICH_TIME, needModifyPlan.getWt());
        startActivityForResult(NewEditTimeActivity.class, bundle, Values.REQUEST_EDIT_TIME);
    }

    @OnClick(R2.id.tv_control_device)
    public void onTvControlDeviceClicked() {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Keys.PARENT_CONTROL_DEVICE, needModifyPlan.getsMac());
        startActivityForResult(ChooseControlMobileActivity.class, bundle, Values.REQUEST_EDIT_DEVICE);
    }

    private void showModifyNameDialog() {
        if (modifyNameDialog == null) {
            modifyNameDialog = new TInputDialog(this);
            modifyNameDialog.setTitle(R.string.modify_name);
            modifyNameDialog.setEditHint(R.string.input_new_name_please);
            modifyNameDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    modifyNameDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    hasModify = true;
                    tvBanAppName.setText(s);
                    if (needModifyPlan != null) {
                        needModifyPlan.setName(s);
                    }
                    modifyNameDialog.dismiss();
                }
            });
        }
        modifyNameDialog.clearInputText();
        modifyNameDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            hasModify = true;
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            switch (requestCode) {
                case Values.REQUEST_EDIT_TIME:
                    String startTime = bundle.getString(Keys.START_TIME);
                    String endTime = bundle.getString(Keys.END_TIME);
                    int whichTime = bundle.getInt(Keys.WHICH_TIME);
                    needModifyPlan.setSt(startTime);
                    needModifyPlan.setEt(endTime);
                    needModifyPlan.setWt(whichTime);
                    TLog.i("kkkkk", " startTime = %s, endTime = %s, whichTime = %s", startTime, endTime, whichTime);
                    break;
                case Values.REQUEST_EDIT_DEVICE:
                    ArrayList<String> stringArrayList = bundle.getStringArrayList(Keys.PARENT_CONTROL_DEVICE);
                    needModifyPlan.setsMac(stringArrayList);
                    TLog.i(stringArrayList);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onSetAllowTimeResponse(BaseResponse response) {
        switch (response.getCode()) {
            case ApiCode.SUCC:
                hasModify = false;
                ToastUtils.showShort(R.string.set_option_success);
                dismiss(unsavedDialog);
                onTitleLeftClick();
                break;
            default:
                ToastUtils.showShort(R.string.option_failed_retry);
                break;
        }
    }

    @Override
    public void onDeleteAllowTimeResponse(BaseResponse response) {

    }

    @Override
    protected void onTitleLeftClick() {
        if (hasModify) {
            showUnsavedDialog();
        } else {
            super.onTitleLeftClick();
        }
    }

    private void showUnsavedDialog() {
        if (unsavedDialog == null) {
            unsavedDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.tips)
                    .content("您有修改的配置尚未保存，是否立即保存？")
                    .left("放弃")
                    .right("保存")
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            hasModify = false;
                            onTitleLeftClick();
                        }

                        @Override
                        public void onClickRight(View view) {
                            if (Check.hasContent(needModifyPlan.getSt()) && Check.hasContent(needModifyPlan.getEt()) && needModifyPlan.getWt() > 0) {
                                if (Check.hasContent(needModifyPlan.getsMac())) {
                                    mPresenter.setTimeControlPlan(MyApplication.getAppContext().getCurrentSelectNode(),
                                            needModifyPlan.getId(), tvBanAppName.getText().toString(),
                                            needModifyPlan.getSt(), needModifyPlan.getEt(), needModifyPlan.getWt(),
                                            needModifyPlan.getsMac());
                                } else {
                                    ToastUtils.showShort("请选择需要管理的设备");
                                    dismiss(unsavedDialog);
                                }
                            } else {
                                ToastUtils.showShort("请选择需要管理的时间段");
                                dismiss(unsavedDialog);
                            }
                        }
                    });
        }
        unsavedDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismiss(unsavedDialog);
        super.onDestroy();
    }

}
