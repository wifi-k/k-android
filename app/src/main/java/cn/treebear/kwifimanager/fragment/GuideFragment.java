package cn.treebear.kwifimanager.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.chaychan.library.BottomBarItem;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.config.Config;

public class GuideFragment extends BaseFragment {
    ImageView ivBind;
    TextView tvInputFamilyCode;
    BottomBarItem bottomBarGallery;

    @Override
    public int layoutId() {
        return R.layout.fragment_unbind_guide;
    }

    @Override
    protected boolean useButterKnife() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        ivBind = (ImageView) mRootView.findViewById(R.id.iv_bind);
        tvInputFamilyCode = (TextView) mRootView.findViewById(R.id.tv_input_family_code);
        bottomBarGallery = (BottomBarItem) mRootView.findViewById(R.id.bottom_bar_gallery);
        initGuideView();
    }

    private void initGuideView() {
        NewbieGuide.with(this)
                .setLabel("guide1")
                .alwaysShow(true)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(ivBind, HighLight.Shape.ROUND_RECTANGLE)
//                        .addHighLightWithOptions(ivBind,options1)
                        .setBackgroundColor(Config.Colors.COLOR_80BLACK)
                        .setLayoutRes(R.layout.layout_guide_1))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tvInputFamilyCode)
                        .setBackgroundColor(Config.Colors.COLOR_80BLACK)
                        .setLayoutRes(R.layout.layout_guide_2))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(bottomBarGallery)
                        .setBackgroundColor(Config.Colors.COLOR_80BLACK)
                        .setLayoutRes(R.layout.layout_guide_3))
                .show();
    }
}
