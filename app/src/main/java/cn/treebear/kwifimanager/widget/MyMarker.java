package cn.treebear.kwifimanager.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.xujiaji.happybubble.BubbleLayout;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.util.DensityUtil;

//import com.github.mikephil.charting.components.MarkerView;

/**
 * Custom implementation of the MarkerView.
 */
@SuppressLint("ViewConstructor")
public class MyMarker extends MarkerView {

    private final TextView tvContent;
    private final BubbleLayout bubble;
    private CallBack mCallBack;

    public MyMarker(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tv_content);
        bubble = findViewById(R.id.bubble_wrapper);
        bubble.setLookPosition(3);
    }

    @Override
    public MPPointF getOffset() {
        bubble.setLookPosition(DensityUtil.dip2px(6));
        return new MPPointF(0, -getHeight());
    }

    @Override
    public MPPointF getOffsetRight() {
        bubble.setLookPosition(DensityUtil.dip2px(36));
        return new MPPointF(-getWidth(), -getHeight());
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String values;
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            values = "" + Utils.formatNumber(ce.getHigh(), 0, true);
        } else {
            values = "" + Utils.formatNumber(e.getY(), 0, true);
        }
        tvContent.setText(values);
        if (mCallBack != null) {
            mCallBack.onCallBack(e.getX(), values);
        }
        super.refreshContent(e, highlight);
    }

    public void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public interface CallBack {
        void onCallBack(float x, String value);
    }

    public TextView getTvContent() {
        return tvContent;
    }
}
