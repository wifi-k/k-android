package cn.treebear.kwifimanager.activity.me;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.BuildConfig;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.account.SignInActivity;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.server.contract.SignOutContract;
import cn.treebear.kwifimanager.mvp.server.presenter.SignOutPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.UserInfoUtil;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * @author Administrator
 */
public class SettingsActivity extends BaseActivity<SignOutContract.Presenter, BaseResponse> implements SignOutContract.View {

    @BindView(R2.id.tv_version)
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

    @OnClick(R2.id.tv_about_us)
    public void onTvAboutUsClick() {
        openWebsite("https://www.treebear.net", true, "", false);
    }

    @OnClick(R2.id.tv_sign_out)
    public void onTvSignOutClick() {
        showSignOutDialog();
    }

    private void showSignOutDialog() {
        if (signOutDialog == null) {
            signOutDialog = new TMessageDialog(this).withoutMid()
                    .title("退出登录")
                    .content("确认退出小k云管家吗？")
                    .left("取消")
                    .right("确认")
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            dismiss(signOutDialog);
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
        dismiss(signOutDialog);
        UserInfoUtil.clearUserInfo();
//        startActivity(LaunchAccountActivity.class);
        startActivity(SignInActivity.class);
        ActivityStackUtils.finishAll(Config.Tags.ALL);
    }
}
