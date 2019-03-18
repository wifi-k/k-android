package net.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.SelectDaysAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.Daybean;
import net.treebear.kwifimanager.bean.TimeLimitBean;
import net.treebear.kwifimanager.config.ConstConfig;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.widget.TimePickerPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private TimeLimitBean timeLimitBean;
    private List<Daybean> days = new ArrayList<>();
    private TimePickerPop startTimePop;
    private TimePickerPop endTimePop;
    private SelectDaysAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.activity_new_edit_time;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            ArrayList<TimeLimitBean> times = params.getParcelableArrayList(Keys.TIME_LIMIT_TIME);
            days.addAll(ConstConfig.DAY_OF_WEEK);
            if (times == null || times.size() == 0) {
                return;
            }
            timeLimitBean = times.get(0);
            if (timeLimitBean.getDays() == null) {
                return;
            }
            for (Daybean d : timeLimitBean.getDays()) {
                for (Daybean day : days) {
                    if (day.getCode() == d.getCode()) {
                        day.setChecked(true);
                    }
                }
            }
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.edit_time, R.string.save);
        if (timeLimitBean != null) {
            tvStartTime.setText(timeLimitBean.getStartTime());
            tvEndTime.setText(timeLimitBean.getEndTime());
        }
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
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        timeLimitBean.setDays(filter(adapter.getData()));
        bundle.putParcelable(Keys.TIME_LIMIT_TIME, timeLimitBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onTitleLeftClick();
    }

    private ArrayList<Daybean> filter(List<Daybean> data) {
        ArrayList<Daybean> result = new ArrayList<>();
        for (Daybean datum : data) {
            if (datum.isChecked()) {
                result.add(datum);
            }
        }
        return result;
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
                    timeLimitBean.setStartTime(time);
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
                    timeLimitBean.setEndTime(time);
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

}
