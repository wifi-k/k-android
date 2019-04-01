package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.SelectDaysAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.Daybean;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.util.NumberUtil;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.pop.TimePickerPop;

/**
 * @author Administrator
 */
public class NewEditTimeActivity extends BaseActivity {

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rv_days)
    RecyclerView recyclerView;
    private List<Daybean> days = new ArrayList<>();
    private TimePickerPop startTimePop;
    private TimePickerPop endTimePop;
    private SelectDaysAdapter adapter;
    private String startTime = "";
    private String endTime = "";
    private int whichTime = 0;
    private boolean[] WEEK;

    @Override
    public int layoutId() {
        return R.layout.activity_new_edit_time;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            startTime = params.getString(Keys.START_TIME);
            endTime = params.getString(Keys.END_TIME);
            whichTime = params.getInt(Keys.WHICH_TIME, 0);
            days.addAll(ConstConfig.DAY_OF_WEEK);
            TLog.i("startTime", Arrays.toString(WEEK));
            WEEK = NumberUtil.encodeBinary(whichTime);
            for (int i = 0; i < WEEK.length; i++) {
                if (i < WEEK.length - 1) {
                    days.get(i).setChecked(WEEK[i + 1]);
                }
            }
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.edit_time, R.string.save);
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        adapter = new SelectDaysAdapter(days);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.start_time_wrapper)
    public void onStartTimeWrapperClicked() {
        showModifyStartTimePop();
    }

    @OnClick(R.id.end_time_wrapper)
    public void onEndTimeWrapperClicked() {
        showModifyEndTimePop();
    }

    @Override
    protected void onTitleRightClick() {
        updateWeek();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.START_TIME, tvStartTime.getText().toString());
        bundle.putString(Keys.END_TIME, tvEndTime.getText().toString());
        bundle.putInt(Keys.WHICH_TIME, NumberUtil.decodeBinary(WEEK));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onTitleLeftClick();
    }

    private void updateWeek() {
        List<Daybean> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            WEEK[i + 1] = data.get(i).isChecked();
        }
        TLog.i("startTime", Arrays.toString(WEEK));
    }

    private void showModifyStartTimePop() {
        if (startTimePop == null) {
            startTimePop = new TimePickerPop(this);
            startTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    startTimePop.dismiss();
                }

                @Override
                public void onSelected(String time) {
                    startTime = time;
                    tvStartTime.setText(time);
                    startTimePop.dismiss();
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        backgroundAlpha(0.8f);
        startTimePop.show(getWindow().getDecorView());
    }

    private void showModifyEndTimePop() {
        if (endTimePop == null) {
            endTimePop = new TimePickerPop(this);
            endTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    endTimePop.dismiss();
                }

                @Override
                public void onSelected(String time) {
                    endTime = time;
                    tvEndTime.setText(time);
                    endTimePop.dismiss();
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        backgroundAlpha(0.8f);
        endTimePop.show(getWindow().getDecorView());
    }

    @Override
    protected void onDestroy() {
        dismiss(endTimePop, startTimePop);
        super.onDestroy();
    }
}
