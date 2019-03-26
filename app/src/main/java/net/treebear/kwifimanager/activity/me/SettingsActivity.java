package net.treebear.kwifimanager.activity.me;

import android.view.View;
import android.widget.TextView;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.server.contract.SignOutContract;
import net.treebear.kwifimanager.mvp.server.presenter.SignOutPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.widget.dialog.TMessageDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class SettingsActivity extends BaseActivity<SignOutContract.Presenter, BaseResponse> implements SignOutContract.View {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    private TMessageDialog signOutDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        tvVersion.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public SignOutContract.Presenter getPresenter() {
        return new SignOutPresenter();
    }

    @OnClick({R.id.tv_version_update, R.id.tv_version})
    public void onViewClicked(View view) {
        startActivity(UpdateAppActivity.class);
    }

    @OnClick(R.id.tv_about_us)
    public void onTvAboutUsClick() {
        // TODO: 2019/3/14 跳转关于我们
    }

    @OnClick(R.id.tv_sign_out)
    public void onTvSignOutClick() {
        showSignOutDialog();
    }

    private void showSignOutDialog() {
        if (signOutDialog == null) {
            signOutDialog = new TMessageDialog(this).withoutMid()
                    .title("退出登录")
                    .content("确认退出小k管家吗？")
                    .left("取消")
                    .right("确认")
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            signOutDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            mPresenter.signOut();
                        }
                    });
        }
        signOutDialog.show();
    }


    @Override
    public void onSignOut() {
        ActivityStackUtils.finishAll(Config.Tags.ALL);
    }
}
