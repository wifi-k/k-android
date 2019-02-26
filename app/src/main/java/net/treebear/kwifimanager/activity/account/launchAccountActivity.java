package net.treebear.kwifimanager.activity.account;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.MainActivity;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.mvp.contract.AdvertisementContract;
import net.treebear.kwifimanager.mvp.presenter.AdvertisementPresenter;

import butterknife.OnClick;

/**
 * @author Tinlone
 * 启动登录页
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

    }

    @OnClick(R.id.btn_signin)
    public void onSignInClick() {
//        startActivity(SignInActivity.class);
        startActivity(MainActivity.class);
    }

    @OnClick(R.id.btn_signup)
    public void onSignUpClick() {
        startActivity(SignUpActivity.class);
    }

    @Override
    public AdvertisementContract.IAdvertisementPresenter getPresenter() {
        return new AdvertisementPresenter();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
