package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;
import cn.treebear.kwifimanager.bean.MobilePhoneBean;
import cn.treebear.kwifimanager.bean.TimeLimitBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

/**
 * @author Administrator
 */
public class TimeControlPlanActivity extends BaseActivity {

    @BindView(R.id.tv_ban_app_name)
    TextView tvBanAppName;
    @BindView(R.id.tv_ban_app_tips)
    TextView tvBanAppTips;
    @BindView(R.id.tv_modify_name)
    TextView tvModifyName;
    @BindView(R.id.tv_limited_online_time)
    TextView tvLimitedTime;
    private BanAppPlanBean needModifyPlan;
    private TInputDialog modifyNameDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_ban_app_plan;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            needModifyPlan = (BanAppPlanBean) params.getSerializable(Keys.BAN_APP_PLAN);
        }
    }

    @Override
    protected void initView() {
        if (needModifyPlan != null) {
            change2ModifyDisplay();
        } else {
            change2IncreaseDisplay();
        }
        tvLimitedTime.setText(R.string.limited_online_time);
        tvBanAppTips.setText(R.string.choose_ban_time_and_device);
    }

    private void change2IncreaseDisplay() {
        setTitleBack(R.string.increase, R.string.save);
        tvBanAppName.setText(R.string.ban_app_plan_1);
    }

    private void change2ModifyDisplay() {
        setTitleBack(R.string.edit, R.string.save);
        if (needModifyPlan == null) {
            return;
        }
        tvBanAppName.setText(needModifyPlan.getName());
    }

    @OnClick(R.id.tv_modify_name)
    public void onTvModifyNameClicked() {
        showModifyNameDialog();
    }

    @OnClick(R.id.tv_limited_online_time)
    public void onTvLimitedOnlineTimeClicked() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.TIME_LIMIT_TIME, needModifyPlan.getLimitOnlineTime());
        startActivityForResult(NewEditTimeActivity.class, bundle, Values.REQUEST_EDIT_TIME);
    }

    @OnClick(R.id.tv_control_device)
    public void onTvControlDeviceClicked() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.PARENT_CONTROL_DEVICE, needModifyPlan.getBanMobile());
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
                    // TODO: 2019/3/15 修改名称
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
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            switch (requestCode) {
                case Values.REQUEST_EDIT_TIME:
                    TimeLimitBean timeLimitBean = bundle.getParcelable(Keys.TIME_LIMIT_TIME);
                    needModifyPlan.getLimitOnlineTime().set(0, timeLimitBean);
                    TLog.i(timeLimitBean);
                    break;
                case Values.REQUEST_EDIT_DEVICE:
                    ArrayList<MobilePhoneBean> devices = bundle.getParcelableArrayList(Keys.PARENT_CONTROL_DEVICE);
                    needModifyPlan.setBanMobile(devices);
                    TLog.i(devices);
                    break;
                default:
                    break;
            }
        }
    }
}
