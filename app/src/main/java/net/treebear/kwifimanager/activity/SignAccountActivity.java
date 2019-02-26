package net.treebear.kwifimanager.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import net.treebear.kwifimanager.R;

import net.treebear.kwifimanager.BuildConfig;
import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.AdvertisementBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.contract.AdvertisementContract;
import net.treebear.kwifimanager.mvp.presenter.AdvertisementPresenter;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.CountObserver;
import net.treebear.kwifimanager.util.CountUtil;
import net.treebear.kwifimanager.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * @author Tinlone
 * 启动登录页
 * Let life be beautiful like summer flowers and death like autumn leaves.
 */
public class SignAccountActivity extends BaseActivity {


    @Override
    public int layoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView() {
        statusTransparentFontWhite();

    }

    @OnClick(R.id.btn_signin)
    public void onSignInClick(){
        // TODO: 2019/2/26 跳转到登录页
    }
    @OnClick(R.id.btn_signup)
    public void onSignUpClick(){
        // TODO: 2019/2/26 跳转到注册页
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
