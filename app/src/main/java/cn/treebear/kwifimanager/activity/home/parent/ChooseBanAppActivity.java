package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.ChooseAppAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.AppBean;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.test.BeanTest;

/**
 * @author Administrator
 */
public class ChooseBanAppActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView rvAppList;
    private BanAppPlanBean needModifyPlan;
    ArrayList<AppBean> appList = new ArrayList<>();
    private ChooseAppAdapter chooseAppAdapter;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public void initParams(Bundle params) {
        needModifyPlan = (BanAppPlanBean) params.getSerializable(Keys.BAN_APP_PLAN);
        ArrayList<AppBean> apps = BeanTest.getAppList();
        if (needModifyPlan != null) {
            List<AppBean> banApps = needModifyPlan.getBanApps();
            for (AppBean app : banApps) {
                for (AppBean bena : apps) {
                    if (bena.getName().equals(app.getName())) {
                        bena.setBan(true);
                    }
                }
            }
        }
        appList.addAll(apps);
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.choose_ban_app, R.string.save);
        chooseAppAdapter = new ChooseAppAdapter(appList);
        rvAppList.setLayoutManager(new GridLayoutManager(this, 3));
        rvAppList.setAdapter(chooseAppAdapter);
        chooseAppAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                appList.get(position).setBan(!appList.get(position).isBan());
                adapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    protected void onTitleRightClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.BAN_APP_PLAN, convert(appList));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onTitleLeftClick();
    }

    private ArrayList<AppBean> convert(ArrayList<AppBean> appList) {
        ArrayList<AppBean> result = new ArrayList<>();
        for (AppBean bean : appList) {
            if (bean.isBan()) {
                result.add(bean);
            }
        }
        return result;
    }
}
