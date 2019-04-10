package cn.treebear.kwifimanager.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SlideableViewPager extends ViewPager {

    private boolean slideable = false;

    public SlideableViewPager(@NonNull Context context) {
        super(context);
    }

    public SlideableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (slideable) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (slideable) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public void setSlideable(boolean slideable) {
        this.slideable = slideable;
    }
}
