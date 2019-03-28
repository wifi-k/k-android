package cn.treebear.kwifimanager.activity.home.parent;

import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseActivity;

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
