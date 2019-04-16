package cn.treebear.kwifimanager.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.xujiaji.happybubble.BubbleLayout;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.util.TLog;

/**
 * Custom implementation of the MarkerView.
 */
@SuppressLint("ViewConstructor")
public class MyMarker extends MarkerView {

    private final TextView tvContent;
    private final BubbleLayout bubble;

    public MyMarker(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tv_content);
        bubble = findViewById(R.id.bubble_wrapper);
        bubble.setLookPosition(3);
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            TLog.i(Utils.formatNumber(e.getY(), 0, true));
            tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
        }
//        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-24, -getHeight() + 10);
    }
}
