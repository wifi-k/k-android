package cn.treebear.kwifimanager.activity.me;

import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.BuildConfig;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;

/**
 * @author Administrator
 */
public class UpdateAppActivity extends BaseActivity {

    @BindView(R2.id.tv_current_version)
    TextView tvCurrentVersion;
    @BindView(R2.id.tv_newer_version)
    TextView tvNewerVersion;

    @Override
    public int layoutId() {
        return R.layout.activity_update_app;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.version_update);
        tvCurrentVersion.setText(BuildConfig.VERSION_NAME);
        tvNewerVersion.setText(BuildConfig.VERSION_NAME);
    }

    @OnClick(R2.id.tv_uodate_now)
    public void onViewClicked() {
        ToastUtils.showShort("已是最新版本");
    }
}
