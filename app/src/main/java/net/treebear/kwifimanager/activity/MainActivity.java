package net.treebear.kwifimanager.activity;

import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.chaychan.library.BottomBarLayout;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.PagerFragmentAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.fragment.BlankFragment;
import net.treebear.kwifimanager.fragment.HomeBindFragment;
import net.treebear.kwifimanager.fragment.HomeUnbindFragment;
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
    private PagerFragmentAdapter fragmentAdapter;

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
        fragmentAdapter = new PagerFragmentAdapter(getSupportFragmentManager(), fragments);
        vpFragments.setAdapter(new PagerFragmentAdapter(getSupportFragmentManager(), fragments));
        bottomBar.setViewPager(vpFragments);
        bottomBar.setOnItemSelectedListener((bottomBarItem, i, i1) -> {
            switch (i) {
                case 0:
                    statusWhiteFontBlack();
                    break;
                case 1:

                    break;
                case 2:

                    break;
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragments.get(0) instanceof HomeBindFragment && !MyApplication.getAppContext().hasAuth()) {
            fragments.set(0, homeUnbindFragment);
        } else {
            fragments.set(0, homeBindFragment);
        }
        fragmentAdapter.notifyDataSetChanged();
        ToastUtils.showLong(MyApplication.getAppContext().getUser().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
