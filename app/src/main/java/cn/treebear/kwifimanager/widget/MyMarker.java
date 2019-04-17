package cn.treebear.kwifimanager.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.util.DensityUtil;

//import com.github.mikephil.charting.components.MarkerView;

/**
 * Custom implementation of the MarkerView.
 */
@SuppressLint("ViewConstructor")
public class MyMarker extends MarkerView {

    private final TextView tvContent;
    private final View rootView;
    private String oldValue = "";
    private String values;

    public MyMarker(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tv_content);
        rootView = findViewById(R.id.maker_root);
    }

    @Override
    public MPPointF getOffset() {
//        if (!oldValue.equals(values)) {
        rootView.setBackgroundResource(R.mipmap.marker_left);
        rootView.invalidate();
        oldValue = values;
//        }
        return new MPPointF(0 - DensityUtil.dip2px(4), -getHeight() + DensityUtil.dip2px(4));
    }

    @Override
    public MPPointF getOffsetRight() {
//        if (!oldValue.equals(values)) {
        rootView.setBackgroundResource(R.mipmap.marker_right);
        rootView.invalidate();
        oldValue = values;
//        }
        return new MPPointF(-getWidth() + DensityUtil.dip2px(4), -getHeight() + DensityUtil.dip2px(6));
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            values = "" + Utils.formatNumber(ce.getHigh(), 0, true);
        } else {
            values = "" + Utils.formatNumber(e.getY(), 0, true);
        }
        tvContent.setText(values);
        super.refreshContent(e, highlight);
    }

    public TextView getTvContent() {
        return tvContent;
    }
}
