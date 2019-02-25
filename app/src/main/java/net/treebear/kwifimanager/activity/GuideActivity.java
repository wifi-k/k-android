package net.treebear.kwifimanager.activity;

import android.support.v4.view.ViewPager;

import net.treebear.kwifimanager.R;

import net.treebear.kwifimanager.adapter.GuideAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.IPresenter;

import butterknife.BindView;

/**
 * @author Tinlone
 * Let this be my last word, that I trust in thy love.
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.vpGuide)
    ViewPager vpGuide;

    @Override
    public int layoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public IPresenter getPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        GuideAdapter guideAdapter = new GuideAdapter(this);
        vpGuide.setAdapter(guideAdapter);
    }

}
