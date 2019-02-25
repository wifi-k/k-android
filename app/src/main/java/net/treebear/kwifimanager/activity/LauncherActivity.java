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
 * 启动广告页
 * Let life be beautiful like summer flowers and death like autumn leaves.
 */
public class LauncherActivity extends BaseActivity<AdvertisementContract.IAdvertisementPresenter, AdvertisementBean> implements AdvertisementContract.IAdvertisementView {

    @BindView(R.id.tvSkip)
    TextView tvSkip;
    @BindView(R.id.ivAdvertisement)
    ImageView ivAdvertisement;
    @BindView(R.id.ivBottomLogo)
    ImageView ivBottomLogo;
    private Disposable disposable;

    @Override
    public int layoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView() {
        skipDown();
    }

    @Override
    protected void initData() {
        mPresenter.getAdvertisement();
    }

    @Override
    public AdvertisementContract.IAdvertisementPresenter getPresenter() {
        return new AdvertisementPresenter();
    }

    @Override
    public void onLoadData(AdvertisementBean resultData) {
        if (resultData != null && Check.isLegalWebSite(resultData.getPicture())) {
            Glide.with(this)
                    .load(resultData.getPicture())
                    .into(ivAdvertisement);
        }
    }

    private void skipDown() {
        CountUtil.numberDown(Config.Numbers.ADVERTISEMENT_TIME, new CountObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long t) {
                tvSkip.setText(String.format("跳过%ss", t - 1 < 0 ? 0 : t - 1));
            }

            @Override
            public void onComplete() {
                doNext();
            }
        });
    }

    @OnClick(R.id.tvSkip)
    public void onSkipClick() {
        dispose(disposable);
        doNext();
    }


    private void doNext() {
        int spVersionCode = (int) SharedPreferencesUtil.getParam(MyApplication.getAppContext(),
                SharedPreferencesUtil.VERSION_CODE, -1);
        if (BuildConfig.VERSION_CODE > spVersionCode) {
            SharedPreferencesUtil.setParam(MyApplication.getAppContext(),
                    SharedPreferencesUtil.VERSION_CODE, BuildConfig.VERSION_CODE);
            startActivity(GuideActivity.class);
        } else {
            startActivity(MainActivity.class);
        }

        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose(disposable);
    }
}
