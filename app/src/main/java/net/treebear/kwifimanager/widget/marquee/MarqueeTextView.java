package net.treebear.kwifimanager.widget.marquee;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import net.treebear.kwifimanager.R;

import java.util.List;

/**
 * @author Admin
 */
public class MarqueeTextView extends LinearLayout {

    private Context mContext;
    private ViewFlipper viewFlipper;
    private View marqueeTextView;
    private List<String> textArrays;
    private MarqueeTextViewClickListener marqueeTextViewClickListener;

    public MarqueeTextView(Context context) {
        super(context);
        mContext = context;
        initBasicView();
    }


    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBasicView();
    }

    public void setTextArraysAndClickListener(List<String> textArrays, MarqueeTextViewClickListener marqueeTextViewClickListener) {//1.设置数据源；2.设置监听回调（将textView点击事件传递到目标界面进行操作）
        this.textArrays = textArrays;
        this.marqueeTextViewClickListener = marqueeTextViewClickListener;
        initMarqueeTextView(textArrays, marqueeTextViewClickListener);
    }

    /**
     * 加载布局，初始化ViewFlipper组件及效果
     */
    public void initBasicView() {
        marqueeTextView = LayoutInflater.from(mContext).inflate(R.layout.marquee_textview_layout, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(marqueeTextView, layoutParams);
        viewFlipper = marqueeTextView.findViewById(R.id.viewFlipper);
        //设置上下的动画效果（自定义动画，所以改左右也很简单）
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        viewFlipper.startFlipping();
    }

    public void initMarqueeTextView(List<String> textArrays, MarqueeTextViewClickListener marqueeTextViewClickListener) {
        if (textArrays.size() == 0) {
            return;
        }

        int i = 0;
        viewFlipper.removeAllViews();
        while (i < textArrays.size()) {
            TextView textView = new TextView(mContext);
            textView.setText(textArrays.get(i));
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_notice));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            textView.setOnClickListener(marqueeTextViewClickListener);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            viewFlipper.addView(textView, lp);
            i++;
        }
        viewFlipper.startFlipping();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseResources();
    }

    private void releaseResources() {
        if (marqueeTextView != null) {
            if (viewFlipper != null) {
                viewFlipper.stopFlipping();
                viewFlipper.removeAllViews();
                viewFlipper = null;
            }
            marqueeTextView = null;
        }
    }

}
