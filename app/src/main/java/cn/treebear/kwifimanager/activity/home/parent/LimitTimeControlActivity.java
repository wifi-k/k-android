package cn.treebear.kwifimanager.activity.home.parent;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.LimitOnlineTimeAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;
import cn.treebear.kwifimanager.bean.TimeLimitBean;
import cn.treebear.kwifimanager.config.Keys;

/**
 * @author Administrator
 */
@Deprecated
public class LimitTimeControlActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
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

    @OnClick(R2.id.tv_add_new_limit_time)
    public void onViewClicked() {

    }

    private void toEditTime(final int position) {

    }

    private void showModifyTimeNameDialog() {

    }

    private void showDeleteTimeDialog() {

    }

}
