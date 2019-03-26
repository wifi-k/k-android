package net.treebear.kwifimanager.activity;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.chaychan.library.BottomBarLayout;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseFragmentActivity;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.fragment.GalleryFragment;
import net.treebear.kwifimanager.fragment.HomeBindFragment;
import net.treebear.kwifimanager.fragment.HomeUnbindFragment;
import net.treebear.kwifimanager.fragment.MeFragment;
import net.treebear.kwifimanager.http.WiFiHttpClient;
import net.treebear.kwifimanager.mvp.IModel;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.NetWorkUtils;
import net.treebear.kwifimanager.util.TLog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Tinlone
 * <h2>主界面大框架</h2>
 * We read the world wrong and say that it deceives us.
 */
public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.vp_fragments)
    FrameLayout vpFragments;
    @BindView(R.id.bottom_bar)
    BottomBarLayout bottomBar;
    HomeBindFragment homeBindFragment;
    HomeUnbindFragment homeUnbindFragment;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>() {
        {
            homeBindFragment = new HomeBindFragment();
            homeUnbindFragment = new HomeUnbindFragment();
            if (MyApplication.getAppContext().hasBoundNode()) {
                add(0, homeBindFragment);
            } else {
                add(0, homeUnbindFragment);
            }
            add(1, new GalleryFragment());
            add(2, new MeFragment());
        }
    };
    long lastPressBackMills = 0;
    boolean isTryingSign = false;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public IPresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_MODIFY_USER_MOBILE,this);
        addFragments(fragments);
        if (MyApplication.getAppContext().hasBoundNode()) {
            statusWhiteFontBlack();
        } else {
            statusTransparentFontWhite();
        }
        bottomBar.setOnItemSelectedListener((bottomBarItem, i, i1) -> {
            updateFragment(i1);
            switch (i1) {
                case 0:
                    if (MyApplication.getAppContext().hasBoundNode()) {
                        statusWhiteFontBlack();
                    } else {
                        statusTransparentFontWhite();
                    }
                    break;
                case 1:
                    statusTransparentFontWhite();
                    break;
                case 2:
                    statusTransparentFontWhite();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isTryingSign && NetWorkUtils.isWifiConnected(this)) {
            tryToSignWifi();
        }
        updateHomeFragment();
        TLog.i(MyApplication.getAppContext().getUser().toString());
        TLog.i(MyApplication.getAppContext().getDeviceInfo());
    }

    @Override
    protected int getFragmentHolderId() {
        return R.id.vp_fragments;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastPressBackMills > 3000) {
            lastPressBackMills = System.currentTimeMillis();
            ToastUtils.showShort(R.string.double_click_for_exit);
        } else {
            finish();
        }
    }

    public void updateHomeFragment() {
        //若已认证
        if (MyApplication.getAppContext().hasBoundNode()) {
            // 当前为未绑定界面
            if (mFragments.get(0) instanceof HomeUnbindFragment) {
                // 切换为已绑定界面
                replaceFragment(0, homeBindFragment);
                statusWhiteFontBlack();
            }
        } else {
            // 若未认证 且当前为绑定界面
            if (mFragments.get(0) instanceof HomeBindFragment) {
                // 切换为未绑定界面
                replaceFragment(0, homeUnbindFragment);
                statusTransparentFontWhite();
            }
        }
    }

    private void tryToSignWifi() {
        isTryingSign = true;
        bottomBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Check.hasContent(MyApplication.getAppContext().getDeviceInfo().getId())) {
                    WiFiHttpClient.tryToSignInWifi(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
                        @Override
                        public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                            isTryingSign = false;
                        }

                        @Override
                        public void onFailed(BaseResponse response,String resultMsg, int resultCode) {
                            isTryingSign = false;
                        }
                    });

                }
                if (NetWorkUtils.isSameLikeXiaoK(MainActivity.this)) {
                    WiFiHttpClient.tryToSignInWifi(new IModel.AsyncCallBack<BaseResponse<WifiDeviceInfo>>() {
                        @Override
                        public void onSuccess(BaseResponse<WifiDeviceInfo> resultData) {
                            isTryingSign = false;
                        }

                        @Override
                        public void onFailed(BaseResponse response,String resultMsg, int resultCode) {
                            isTryingSign = false;
                        }
                    });

                }
            }
        }, 5000);
    }
}
