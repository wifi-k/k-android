package cn.treebear.kwifimanager.activity.home.parent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.BanTimeAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.test.BeanTest;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

/**
 * @author Administrator
 */
public class TimeControlListActivity extends BaseActivity {

    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.rv_ban_app)
    RecyclerView recyclerView;
    private ArrayList<BanAppPlanBean> timeLimitList;
    private int currentModifyPosition;
    private TInputDialog tInputDialog;
    private BanTimeAdapter banTimeAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_ban_app_list;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.online_time_control);
        tvTips.setText(R.string.ban_time_tips);
        timeLimitList = BeanTest.getBanAppPlanList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        banTimeAdapter = new BanTimeAdapter(timeLimitList);
        recyclerView.setAdapter(banTimeAdapter);
        banTimeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                currentModifyPosition = position;
                switch (view.getId()) {
                    case R.id.iv_ban_plan_edit:
                        resetInputDialog();
                        tInputDialog.show();
                        break;
                    case R.id.iv_ban_plan_delete:
                        timeLimitList.remove(position);
                        banTimeAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });
        banTimeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Keys.BAN_APP_PLAN, timeLimitList.get(position));
                startActivity(TimeControlPlanActivity.class, bundle);
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
                    timeLimitList.get(currentModifyPosition).setName(s);
                    tInputDialog.dismiss();
                    banTimeAdapter.notifyDataSetChanged();
                }
            });
        }
        tInputDialog.clearInputText();
    }

    @Override
    protected void onDestroy() {
        dismiss(tInputDialog);
        super.onDestroy();
    }
}
