package cn.treebear.kwifimanager.activity.home.report;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.ActiveAppAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.AppBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.test.BeanTest;
import cn.treebear.kwifimanager.widget.MyMarker;

/**
 * @author Administrator
 */
public class WeekReportActivity extends BaseActivity {

    @BindView(R2.id.chart_week_report)
    LineChart chart;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private String[] values = {"3-24", "3-25", "3-26", "3-27", "3-28", "3-29", "3-30"};
    private ArrayList<AppBean> appList = new ArrayList<>();
    private ActiveAppAdapter activeAppAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_week_report;
    }

    @Override
    protected void initView() {
        setTitleBack("孩子的手机");
        initChart2();
        setAdapter();
    }

    private void setAdapter() {
        appList.addAll(BeanTest.getAppList());
        activeAppAdapter = new ActiveAppAdapter(appList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(activeAppAdapter);
    }


    private void initChart2() {
        {   // // Chart Style // //
            // bg_children_careful color
            chart.setBackgroundColor(Color.WHITE);
            // disable description text
            chart.getDescription().setEnabled(false);
            // enable touch gestures
            chart.setTouchEnabled(true);
            // set listeners
//            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);

            chart.setHighlightPerTapEnabled(true);
            chart.setHighlightPerDragEnabled(false);
            // create marker to display box when values are selected
            MyMarker mv = new MyMarker(this, R.layout.custom_marker_view);
            // Set the marker to the chart
            mv.setChartView(chart);
            chart.setMarker(mv);
            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(false);
            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }

        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        //自定义x轴显示
        XAxisFormatter formatter = new XAxisFormatter(values);
        x.setDrawAxisLine(false);
        x.setDrawGridLines(false);
        //显示个数
        x.setLabelCount(7, true);
        x.setValueFormatter(formatter);
        x.setAvoidFirstLastClipping(false);
        x.setTextColor(Config.Colors.TEXT_7383A2);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Config.Colors.TEXT_7383A2);
        y.setDrawGridLines(true);
        y.setDrawAxisLine(false);
        y.setGridColor(Config.Colors.COLOR_E7);
        y.setGridLineWidth(1);
        y.setAxisMinimum(0);
        y.setAxisMaximum(24);
        setData(7, 23);
        // draw points over time
        chart.animateX(300);

        chart.getAxisRight().setEnabled(false);
        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
    }

    private void setData(int count, int range) {
        Random random = new Random();
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (random.nextInt(range));
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, null);
            set1.setDrawIcons(false);
            set1.setDrawValues(false);
            set1.setCubicIntensity(0.2f);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setHighLightColor(Color.TRANSPARENT);
            // black lines and points
            set1.setColor(Color.parseColor("#50E3C2"));
            set1.setCircleColor(Color.parseColor("#50E3C2"));

            // line thickness and point size
            set1.setLineWidth(2f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(2f);

            // text size of values
            set1.setDrawValues(false);
            set1.setDrawCircles(false);
            // draw selection line as dashed
//            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_main);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.parseColor("#3345F7E7"));
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            // add the data sets
            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

    public static class XAxisFormatter implements IAxisValueFormatter {

        private ArrayList<String> mValues = new ArrayList<>();

        public XAxisFormatter(String[] values) {
            this.mValues.addAll(Arrays.asList(values));
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues.get((int) value % mValues.size());
        }
    }

}
