package cn.treebear.kwifimanager.activity;

import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;

import butterknife.BindView;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseFragmentActivity;
import cn.treebear.kwifimanager.base.IPresenter;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.fragment.GalleryFragment;
import cn.treebear.kwifimanager.fragment.GuideFragment;
import cn.treebear.kwifimanager.fragment.HomeBindFragment;
import cn.treebear.kwifimanager.fragment.HomeUnbindFragment;
import cn.treebear.kwifimanager.fragment.MeFragment;
import cn.treebear.kwifimanager.fragment.SelectPictureFragment;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.NetWorkUtils;
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
    long lastPressBackMills = 0;
    int currentFragmentIndex = 0;
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
    private GuideFragment guideFragment;
    private SelectPictureFragment selectPictureFragment;

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
        addFragments(R.id.vp_fragments, fragments);
        if (MyApplication.getAppContext().hasBoundNode()) {
            statusWhiteFontBlack();
        } else {
            statusTransparentFontWhite();
        }
        bottomBar.setOnItemSelectedListener((bottomBarItem, i, i1) -> {
            updateFragment(R.id.vp_fragments, i1);
            currentFragmentIndex = i1;
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
            guideFragment = new GuideFragment();
            addFragments(R.id.full_screen_fragment_wrapper, guideFragment);
            SharedPreferencesUtil.setParam(Keys.FIRST_IN, true);
        }
    }

    public void hideGuideFragment() {
        if (guideFragment != null) {
            removeFragment(R.id.full_screen_fragment_wrapper, guideFragment, true);
        }
    }

    public void showSelectGalleryFragment() {
        if (selectPictureFragment == null) {
            selectPictureFragment = new SelectPictureFragment();
            addFragments(R.id.full_screen_fragment_wrapper, selectPictureFragment);
        } else {
            findViewById(R.id.full_screen_fragment_wrapper).setVisibility(View.VISIBLE);
            updateFragment(R.id.full_screen_fragment_wrapper, 0);
        }
        TLog.w(String.valueOf(R.id.full_screen_fragment_wrapper) + "----------------" + String.valueOf(R.id.fragment_wrapper));
        selectPictureFragment.updateStatus();
        statusWhiteFontBlack();
    }

    public void hideSelectGalleryFragment() {
        if (selectPictureFragment != null) {
            selectPictureFragment.clearStatus();
            hideFragment(R.id.full_screen_fragment_wrapper, selectPictureFragment, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHomeFragment();
        updateStatusBar();
    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.full_screen_fragment_wrapper).getVisibility() == View.VISIBLE) {
            hideGuideFragment();
            hideSelectGalleryFragment();
            return;
        }
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
            if (getCurrentFragment(R.id.vp_fragments) instanceof HomeUnbindFragment) {
                // 切换为已绑定界面
                replaceFragment(R.id.vp_fragments, 0, homeBindFragment);
            }
            statusWhiteFontBlack();
        } else {
            // 若未认证 且当前为绑定界面
            if (getCurrentFragment(R.id.vp_fragments) instanceof HomeBindFragment) {
                if (!NetWorkUtils.isNetConnected(this)) {
                    return;
                }
                // 切换为未绑定界面
                replaceFragment(R.id.vp_fragments, 0, homeUnbindFragment);
            }
            statusTransparentFontWhite();
        }
    }

    private void updateStatusBar(){
        switch (currentFragmentIndex) {
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
    }

}
