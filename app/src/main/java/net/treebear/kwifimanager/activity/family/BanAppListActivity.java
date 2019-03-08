package net.treebear.kwifimanager.activity.family;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.BanAppAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.BanAppPlanBean;
import net.treebear.kwifimanager.test.BeanTest;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class BanAppListActivity extends BaseActivity {
    @BindView(R.id.rv_ban_app)
    RecyclerView rvBanAppList;
    private ArrayList<BanAppPlanBean> banAppPlanList = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.activity_ban_app_list;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.ban_app);
        banAppPlanList.addAll(BeanTest.getBanAppPlanList());
        rvBanAppList.setLayoutManager(new LinearLayoutManager(this));
        BanAppAdapter banAppAdapter = new BanAppAdapter(banAppPlanList);
        rvBanAppList.setAdapter(banAppAdapter);
    }
}
