package cn.treebear.kwifimanager.activity.home.parent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.BanAppAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.test.BeanTest;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

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
                    showInputDialog();
                    break;
                case R.id.iv_ban_plan_delete:
                    banAppPlanList.remove(position);
                    banAppAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });
        banAppAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Keys.BAN_APP_PLAN, banAppPlanList.get(position));
                startActivity(BanAppPlanActivity.class, bundle);
            }
        });
    }

    @OnClick(R.id.tv_add_ban_app_plan)
    public void ontvAddBanAppPlanClicked() {
        startActivity(BanAppPlanActivity.class);
    }

    /**
     * 修改名称弹窗
     */
    private void showInputDialog() {
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
        tInputDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismiss(tInputDialog);
        super.onDestroy();
    }
}
