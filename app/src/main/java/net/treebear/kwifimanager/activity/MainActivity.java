package net.treebear.kwifimanager.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.chaychan.library.BottomBarLayout;
import net.treebear.kwifimanager.R;

import net.treebear.kwifimanager.adapter.PagerFragmentAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.IPresenter;
import net.treebear.kwifimanager.fragment.BlankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Tinlone
 * <p>
 * We read the world wrong and say that it deceives us.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_fragments)
    ViewPager vpFragments;
    @BindView(R.id.bottom_bar)
    BottomBarLayout bottomBar;
    String msg = "";
    private List<Fragment> fragments = new ArrayList<Fragment>() {
        {
            add(BlankFragment.newInstance(R.string.homepage, "#00B7EE"));
            add(BlankFragment.newInstance(R.string.info, "#EEB7EE"));
            add(BlankFragment.newInstance(R.string.mine, "#FFFACD"));
        }
    };

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
        vpFragments.setAdapter(new PagerFragmentAdapter(getSupportFragmentManager(), fragments));
        bottomBar.setViewPager(vpFragments);
        bottomBar.getBottomItem(0).setUnreadNum(2);
        bottomBar.getBottomItem(1).setMsg("aloha");
        bottomBar.setOnItemSelectedListener((bottomBarItem, i, i1) -> {
            switch (i1) {
                case 0:
                    statusTransparentFontWhite();
                    msg = "透明状态栏白字";
                    break;
                case 1:
                    statusTransparentFontBlack();
                    msg = "透明状态栏黑字";
                    break;
                case 2:
                    statusWhiteFontBlack();
                    msg = "白底状态栏黑字";
                    break;
            }
//            Tips.toastBottom(this,msg, Toast.LENGTH_SHORT);
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadData(Object resultData) {
        hideLoading();
    }

    @Override
    public void onLoadFail(String resultMsg, String resultCode) {
        hideLoading();
    }
}
