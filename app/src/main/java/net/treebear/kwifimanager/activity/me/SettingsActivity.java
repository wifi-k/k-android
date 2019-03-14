package net.treebear.kwifimanager.activity.me;

import android.view.View;
import android.widget.TextView;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    public int layoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        tvVersion.setText(BuildConfig.VERSION_NAME);
    }

    @OnClick({R.id.tv_about_us, R.id.tv_version_update, R.id.tv_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_about_us:
                // TODO: 2019/3/14 跳转关于我们
                break;
            case R.id.tv_version_update:
                break;
            case R.id.tv_version:
                break;
            default:
                break;
        }
    }
}
