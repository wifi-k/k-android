package cn.treebear.kwifimanager.activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseFragmentActivity;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.fragment.GalleryFragment;
import cn.treebear.kwifimanager.fragment.HomeBindFragment;
import cn.treebear.kwifimanager.fragment.HomeUnbindFragment;
import cn.treebear.kwifimanager.fragment.MeFragment;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.SharedPreferencesUtil;
import cn.treebear.kwifimanager.util.TLog;

/**
 * @author Tinlone
 * <h2>主界面大框架</h2>
 * We read the world wrong and say that it deceives us.
 */
public class MainActivity extends BaseFragmentActivity {

    @BindView(R2.id.vp_fragments)
    FrameLayout vpFragments;
    @BindView(R2.id.bottom_bar)
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
        ActivityStackUtils.pressActivity(Config.Tags.TAG_MODIFY_USER_MOBILE, this);
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
                    statusWhiteFontBlack();
                    break;
                case 2:
                    statusTransparentFontWhite();
                    break;
                default:
                    break;
            }
        });
        checkFirstIn();
    }

    private void checkFirstIn() {
        if (!(boolean) SharedPreferencesUtil.getParam(Keys.FIRST_IN, false) && !MyApplication.getAppContext().hasBoundNode()) {
            ImageView ivGuide = findViewById(R.id.iv_guide);
            final int[] position = {0};
            ivGuide.setVisibility(View.VISIBLE);
            ivGuide.setImageResource(ConstConfig.HOME_GUIDE_IMAGE_RESID.get(position[0]));
            ivGuide.setOnClickListener(v -> {
                if ((++position[0]) < ConstConfig.HOME_GUIDE_IMAGE_RESID.size()) {
                    ivGuide.setImageResource(ConstConfig.HOME_GUIDE_IMAGE_RESID.get(position[0]));
                } else {
                    ivGuide.setVisibility(View.GONE);
                }
            });
            SharedPreferencesUtil.setParam(Keys.FIRST_IN, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

}
