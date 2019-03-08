package net.treebear.kwifimanager.activity.home;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.home.banapp.BanAppListActivity;
import net.treebear.kwifimanager.activity.home.time.TimeControlListActivity;
import net.treebear.kwifimanager.base.BaseActivity;

import butterknife.OnClick;

/**
 * @author Administrator
 */
public class ParentControlActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_parent_control;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.parent_control);
    }

    @OnClick(R.id.tv_time_control)
    public void onTvTimeControlClicked() {
        startActivity(TimeControlListActivity.class);
    }

    @OnClick(R.id.tv_ban_app)
    public void onTvBanAppClicked() {
        startActivity(BanAppListActivity.class);
    }
}
