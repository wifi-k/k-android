package net.treebear.kwifimanager.activity.account;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.util.ActivityStackUtils;

import butterknife.OnClick;

/**
 * @author Tinlone
 * <h2>启动登录页</h2>
 * 可加入渐显动画
 * Let life be beautiful like summer flowers and death like autumn leaves.
 */
public class launchAccountActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_launch_account;
    }

    @Override
    protected void initView() {
        statusTransparentFontWhite();
        ActivityStackUtils.pressActivity(Config.Tags.TAG_LAUNCH_ROOT, this);
    }

    @OnClick(R.id.btn_signin)
    public void onSignInClick() {
        startActivity(SignInActivity.class);
    }

    @OnClick(R.id.btn_signup)
    public void onSignUpClick() {
        startActivity(SignUpActivity.class);
    }

}
