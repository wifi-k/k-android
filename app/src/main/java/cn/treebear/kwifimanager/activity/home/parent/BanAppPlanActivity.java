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
import cn.treebear.kwifimanager.bean.AppBean;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

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
        tvBanApp.setText(R.string.ban_app_ban_app);
        tvBanAppTips.setText(R.string.choose_ban_app_and_device);
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
        if (needModifyPlan != null) {
            bundle.putSerializable(Keys.BAN_APP_PLAN, needModifyPlan);
        }
        startActivityForResult(ChooseBanAppActivity.class, bundle, Values.REQUEST_BAN_APP);
    }

    @OnClick(R.id.tv_control_device)
    public void onTvControlDeviceClicked() {
        Bundle bundle = new Bundle();
        if (needModifyPlan != null) {
//            bundle.getStringArrayList(Keys.PARENT_CONTROL_DEVICE, needModifyPlan.getBanMobile());
        }
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
            switch (requestCode) {
                case Values.REQUEST_BAN_APP:
                    ArrayList<AppBean> apps = bundle.getParcelableArrayList(Keys.BAN_APP_PLAN);
                    needModifyPlan.setBanApps(apps);
                    break;
                case Values.REQUEST_EDIT_DEVICE:
                    break;
                default:
                    break;
            }
        }
    }
}
