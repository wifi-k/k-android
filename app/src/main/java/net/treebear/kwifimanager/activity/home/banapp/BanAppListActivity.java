package net.treebear.kwifimanager.activity.home.banapp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.BanAppAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.BanAppPlanBean;
import net.treebear.kwifimanager.test.BeanTest;
import net.treebear.kwifimanager.widget.TInputDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class BanAppListActivity extends BaseActivity {
    @BindView(R.id.rv_ban_app)
    RecyclerView rvBanAppList;
    private ArrayList<BanAppPlanBean> banAppPlanList = new ArrayList<>();
    private BanAppAdapter banAppAdapter;
    private int currentModifyPosition;
    private TInputDialog tInputDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_ban_app_list;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.ban_app);
        banAppPlanList.addAll(BeanTest.getBanAppPlanList());
        rvBanAppList.setLayoutManager(new LinearLayoutManager(this));
        banAppAdapter = new BanAppAdapter(banAppPlanList);
        rvBanAppList.setAdapter(banAppAdapter);
        banAppAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            currentModifyPosition = position;
            switch (view.getId()) {
                case R.id.iv_ban_plan_edit:
                    resetInputDialog();
                    tInputDialog.show();
                    break;
                case R.id.iv_ban_plan_delete:
                    banAppPlanList.remove(position);
                    banAppAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 修改名称弹窗
     */
    private void resetInputDialog() {
        if (tInputDialog == null) {
            tInputDialog = new TInputDialog(this);
            tInputDialog.setTitle(R.string.modify_name);
            tInputDialog.setEditHint(R.string.input_new_name_please);
            tInputDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    tInputDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/3/8 编辑成员信息
                    banAppPlanList.get(currentModifyPosition).setName(s);
                    tInputDialog.dismiss();
                    banAppAdapter.notifyDataSetChanged();
                }
            });
        }
        tInputDialog.clearInputText();
    }
}
