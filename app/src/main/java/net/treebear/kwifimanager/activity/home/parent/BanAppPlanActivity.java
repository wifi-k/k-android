package net.treebear.kwifimanager.activity.home.parent;

import android.os.Bundle;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.BanAppPlanBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.widget.TInputDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class BanAppPlanActivity extends BaseActivity {


    @BindView(R.id.tv_ban_app_name)
    TextView tvBanAppName;
    @BindView(R.id.tv_limited_online_time)
    TextView tvBanApp;
    @BindView(R.id.tv_ban_app_tips)
    TextView tvBanAppTips;
    private boolean isModifyPlan;
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
            isModifyPlan = needModifyPlan != null;
        }
    }

    @Override
    protected void initView() {
        if (isModifyPlan) {
            change2ModifyDisplay();
        } else {
            change2IncreaseDisplay();
        }
        tvBanApp.setText(R.string.ban_app_ban_app);
        tvBanAppTips.setText(R.string.choose_ban_app_and_device);
    }

    private void change2IncreaseDisplay() {
        setTitleBack(R.string.increase);
        tvBanAppName.setText(R.string.ban_app_plan_1);
    }

    private void change2ModifyDisplay() {
        setTitleBack(R.string.edit);
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
        if (needModifyPlan != null) {
            bundle.putSerializable(Keys.BAN_APP_PLAN, needModifyPlan);
        }
        startActivity(ChooseBanAppActivity.class, bundle);
    }

    @OnClick(R.id.tv_control_device)
    public void onTvControlDeviceClicked() {
        Bundle bundle = new Bundle();
        if (needModifyPlan != null) {
            bundle.putSerializable(Keys.BAN_APP_PLAN, needModifyPlan);
        }
        startActivity(ChooseControlDeviceActivity.class, bundle);
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
    }
}
