package cn.treebear.kwifimanager.fragment;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.library.BottomBarItem;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.widget.GuideView;

public class GuideFragment extends BaseFragment {
    private ImageView ivBind;
    private TextView tvInputFamilyCode;
    private BottomBarItem bottomBarGallery;
    private TextView iv;
    private GuideView guideView1;
    private GuideView guideView2;
    private GuideView guideView3;

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
        ivBind = mRootView.findViewById(R.id.iv_bind_guide);
        tvInputFamilyCode = mRootView.findViewById(R.id.tv_input_code_guide);
        bottomBarGallery = mRootView.findViewById(R.id.bottom_bar_gallery_guide);
        iv = new TextView(mContext);
        iv.setText("欢迎使用");
        iv.setTextColor(Color.WHITE);
        ivBind.post(this::initGuideView);
    }

    private void initGuideView() {
        guideView1 = GuideView.Builder
                .newInstance(mContext)      // 必须调用
                .setTargetView(ivBind)    // 必须调用，设置需要Guide的View
                .setCustomGuideView(iv)  // 必须调用，设置GuideView，可以使任意View的实例，比如ImageView 或者TextView
                .setPaddingOffset(ivBind.getWidth() * 2 / 3, ivBind.getHeight() / 2, ivBind.getWidth() * 2 / 3, ivBind.getHeight() / 2)
                .setDirction(GuideView.Direction.BOTTOM)   // 设置GuideView 相对于TargetView的位置，有八种，不设置则默认在屏幕左上角,其余的可以显示在右上，右下等等
                .setShape(GuideView.MyShape.RECTANGULAR)   // 设置显示形状，支持圆形，椭圆，矩形三种样式，矩形可以是圆角矩形，
                .setBgColor(Config.Colors.COLOR_80BLACK) // 设置背景颜色，默认透明
                .setOnclickExit(false)   // 设置点击消失，可以传入一个Callback，执行被点击后的操作
                .setOnclickListener(this::showGuideView2)
                .setRadius(ivBind.getHeight() / 2)          // 设置圆形或矩形透明区域半径，默认是targetView的显示矩形的半径，如果是矩形，这里是设置矩形圆角大小
                .setOffset(0, 260)     // 设置偏移，一般用于微调GuideView的位置
                .build();   // 必须调用，Buider模式，返回GuideView实例
        guideView1.show();                // 必须调用，显示GuideView
    }

    private void showGuideView2() {
        guideView1.hide();
        guideView2 = GuideView.Builder
                .newInstance(mContext)      // 必须调用
                .setTargetView(tvInputFamilyCode)    // 必须调用，设置需要Guide的View
                .setCustomGuideView(iv)  // 必须调用，设置GuideView，可以使任意View的实例，比如ImageView 或者TextView
                .setPaddingOffset(tvInputFamilyCode.getWidth() * 3 / 5, tvInputFamilyCode.getHeight() * 2 / 3, tvInputFamilyCode.getWidth() * 3 / 5, tvInputFamilyCode.getHeight() * 2 / 3)
                .setDirction(GuideView.Direction.BOTTOM)   // 设置GuideView 相对于TargetView的位置，有八种，不设置则默认在屏幕左上角,其余的可以显示在右上，右下等等
                .setShape(GuideView.MyShape.RECTANGULAR)   // 设置显示形状，支持圆形，椭圆，矩形三种样式，矩形可以是圆角矩形，
                .setBgColor(Config.Colors.COLOR_80BLACK) // 设置背景颜色，默认透明
                .setOnclickExit(false)   // 设置点击消失，可以传入一个Callback，执行被点击后的操作
                .setOnclickListener(this::showGuideView3)
                .setRadius(tvInputFamilyCode.getHeight() * 2 / 3)          // 设置圆形或矩形透明区域半径，默认是targetView的显示矩形的半径，如果是矩形，这里是设置矩形圆角大小
                .setOffset(0, 260)     // 设置偏移，一般用于微调GuideView的位置
                .build();   // 必须调用，Buider模式，返回GuideView实例
        guideView2.show();                // 必须调用，显示GuideView
    }

    private void showGuideView3() {
        guideView2.hide();
        guideView3 = GuideView.Builder
                .newInstance(mContext)      // 必须调用
                .setTargetView(bottomBarGallery)    // 必须调用，设置需要Guide的View
                .setCustomGuideView(iv)  // 必须调用，设置GuideView，可以使任意View的实例，比如ImageView 或者TextView
                .setPaddingOffset(bottomBarGallery.getWidth() / 2, bottomBarGallery.getHeight() / 2, bottomBarGallery.getWidth() / 2, bottomBarGallery.getHeight() / 2)
                .setDirction(GuideView.Direction.TOP)   // 设置GuideView 相对于TargetView的位置，有八种，不设置则默认在屏幕左上角,其余的可以显示在右上，右下等等
                .setShape(GuideView.MyShape.CIRCULAR)   // 设置显示形状，支持圆形，椭圆，矩形三种样式，矩形可以是圆角矩形，
                .setBgColor(Config.Colors.COLOR_80BLACK) // 设置背景颜色，默认透明
                .setOnclickExit(false)   // 设置点击消失，可以传入一个Callback，执行被点击后的操作
                .setOnclickListener(this::finishThis)
                .setRadius(bottomBarGallery.getHeight() / 2)          // 设置圆形或矩形透明区域半径，默认是targetView的显示矩形的半径，如果是矩形，这里是设置矩形圆角大小
                .setOffset(0, -260)     // 设置偏移，一般用于微调GuideView的位置
                .build();   // 必须调用，Buider模式，返回GuideView实例
        guideView3.show();                // 必须调用，显示GuideView
    }

    private void finishThis() {
        guideView3.hide();
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).hidenGuideFragment();
        }
    }
}
