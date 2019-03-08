package net.treebear.kwifimanager.activity.home.healthy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.suke.widget.SwitchButton;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.HealthyModelAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.TimeLimitBean;
import net.treebear.kwifimanager.test.BeanTest;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class HealthyModelActivity extends BaseActivity {

    @BindView(R.id.sb_healthy)
    SwitchButton sbHealthy;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ArrayList<TimeLimitBean> timeLimitList;
    private HealthyModelAdapter healthyModelAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_healthy_model;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.healthy_model);
        timeLimitList = BeanTest.getTimeLimitList();
        sbHealthy.setOnCheckedChangeListener((view, isChecked) -> recyclerView.setEnabled(isChecked));
        healthyModelAdapter = new HealthyModelAdapter(timeLimitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(healthyModelAdapter);
        healthyModelAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                // TODO: 2019/3/8 去修改 
            }
        });
    }

}
