package net.treebear.kwifimanager.activity;

import android.support.v4.app.Fragment;

import com.chaychan.library.BottomBarLayout;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseFragmentPagerAdapter;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.fragment.BlankFragment;
import net.treebear.kwifimanager.fragment.HomeBindFragment;
import net.treebear.kwifimanager.fragment.HomeUnbindFragment;
import net.treebear.kwifimanager.http.WiFiHttpClient;
import net.treebear.kwifimanager.test.BeanTest;
import net.treebear.kwifimanager.util.NetWorkUtils;
import net.treebear.kwifimanager.util.TLog;
import net.treebear.kwifimanager.widget.SlideableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Tinlone
 * <h2>主界面大框架</h2>
 * We read the world wrong and say that it deceives us.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_fragments)
    SlideableViewPager vpFragments;
    @BindView(R.id.bottom_bar)
    BottomBarLayout bottomBar;
    HomeBindFragment homeBindFragment;
    HomeUnbindFragment homeUnbindFragment;
    private List<Fragment> fragments = new ArrayList<Fragment>() {
        {
            homeBindFragment = new HomeBindFragment();
            homeUnbindFragment = new HomeUnbindFragment();
            if (MyApplication.getAppContext().hasAuth()) {
                add(0, homeBindFragment);
            } else {
                add(0, homeUnbindFragment);
            }
            add(1, new BlankFragment());
            add(2, new BlankFragment());
        }
    };
    private BaseFragmentPagerAdapter fragmentAdapter;
    private ArrayList<MobilePhoneBean> mobileList;

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
        fragmentAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        vpFragments.setAdapter(fragmentAdapter);
        bottomBar.setViewPager(vpFragments);
        bottomBar.setOnItemSelectedListener((bottomBarItem, i, i1) -> {
            switch (i) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void initData() {
        // 测试手机设备
        mobileList = BeanTest.getMobilePhoneList(3);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetWorkUtils.isConnectXiaoK(this)) {
            WiFiHttpClient.tryToSignInWifi(null);
        }
        //若已认证
        if (MyApplication.getAppContext().hasAuth()) {
            // 当前为未绑定界面
            if (fragments.get(0) instanceof HomeUnbindFragment) {
                // 切换为已绑定界面
                fragmentAdapter.replaceFragment(0, homeBindFragment);
            }
            statusWhiteFontBlack();
        } else {
            // 若未认证 且当前为绑定界面
            if (fragments.get(0) instanceof HomeBindFragment) {
                // 切换为未绑定界面
                fragmentAdapter.replaceFragment(0, homeUnbindFragment);
            }
            statusTransparentFontWhite();
        }
        fragmentAdapter.notifyDataSetChanged();
        TLog.i(MyApplication.getAppContext().getUser().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
