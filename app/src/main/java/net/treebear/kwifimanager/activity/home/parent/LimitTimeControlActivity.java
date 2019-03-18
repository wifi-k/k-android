package net.treebear.kwifimanager.activity.home.parent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.LimitOnlineTimeAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.BanAppPlanBean;
import net.treebear.kwifimanager.bean.TimeLimitBean;
import net.treebear.kwifimanager.config.Keys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
@Deprecated
public class LimitTimeControlActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private BanAppPlanBean planBean;
    private ArrayList<TimeLimitBean> limitOnlineTime = new ArrayList<>();
    private LimitOnlineTimeAdapter timeAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_limit_time_control;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            ArrayList<TimeLimitBean> times = params.getParcelableArrayList(Keys.TIME_LIMIT_TIME);
            if (times != null) {
                limitOnlineTime.addAll(times);
            }
        }
    }

    @Override
    protected void initView() {
        timeAdapter = new LimitOnlineTimeAdapter(limitOnlineTime);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(timeAdapter);
        timeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_edit_time:
                    showModifyTimeNameDialog();
                    break;
                case R.id.iv_delete_time:
                    showDeleteTimeDialog();
                    break;
                default:
                    break;
            }
        });
        timeAdapter.setOnItemClickListener((adapter, view, position) -> toEditTime(position));
    }

    @OnClick(R.id.tv_add_new_limit_time)
    public void onViewClicked() {

    }

    private void toEditTime(final int position) {

    }

    private void showModifyTimeNameDialog() {

    }

    private void showDeleteTimeDialog() {

    }

}