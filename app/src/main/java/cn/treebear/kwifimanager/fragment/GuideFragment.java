package cn.treebear.kwifimanager.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.library.BottomBarItem;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.widget.GuideView;

public class GuideFragment extends BaseFragment {
    private ImageView ivBind;
    private TextView tvInputFamilyCode;
    private BottomBarItem bottomBarGallery;
    private TextView iv;

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
        iv.setTextColor(getResources().getColor(R.color.white));
        initGuideView();
    }

    private void initGuideView() {
        GuideView.Builder
                .newInstance(mContext)      // 必须调用
                .setTargetView(ivBind)    // 必须调用，设置需要Guide的View
                .setCustomGuideView(iv)  // 必须调用，设置GuideView，可以使任意View的实例，比如ImageView 或者TextView
                .setDirction(GuideView.Direction.LEFT_BOTTOM)   // 设置GuideView 相对于TargetView的位置，有八种，不设置则默认在屏幕左上角,其余的可以显示在右上，右下等等
                .setShape(GuideView.MyShape.RECTANGULAR)   // 设置显示形状，支持圆形，椭圆，矩形三种样式，矩形可以是圆角矩形，
                .setBgColor(Config.Colors.COLOR_80BLACK) // 设置背景颜色，默认透明
                .setOnclickExit(false)   // 设置点击消失，可以传入一个Callback，执行被点击后的操作
                .setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {

                    }
                })
                .setRadius(32)          // 设置圆形或矩形透明区域半径，默认是targetView的显示矩形的半径，如果是矩形，这里是设置矩形圆角大小
                .setOffset(200, 60)     // 设置偏移，一般用于微调GuideView的位置
                .showOnce()             // 设置首次显示，设置后，显示一次后，不再显示
                .build()                // 必须调用，Buider模式，返回GuideView实例
                .show();                // 必须调用，显示GuideView
    }
}
