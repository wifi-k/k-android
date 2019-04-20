package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.SelectDaysAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.Daybean;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.NumberUtil;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;
import cn.treebear.kwifimanager.widget.pop.TimePickerPop;

/**
 * @author Administrator
 */
public class NewEditTimeActivity extends BaseActivity {

    @BindView(R2.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R2.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R2.id.rv_days)
    RecyclerView recyclerView;
    private List<Daybean> days = new ArrayList<>();
    private TimePickerPop startTimePop;
    private TimePickerPop endTimePop;
    private SelectDaysAdapter adapter;
    private String startTime = "";
    private String endTime = "";
    private int whichTime = 0;
    private boolean[] WEEK;
    private boolean hasModify;
    private TMessageDialog unsavedDialog;

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
            for (int i = 0; i < days.size(); i++) {
                days.get(i).setChecked(WEEK[i]);
            }
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.edit_time, R.string.save);
        tvStartTime.setText(Check.hasContent(startTime) ? startTime : (startTime = getString(R.string.default_start_time)));
        tvEndTime.setText(Check.hasContent(endTime) ? endTime : (endTime = getString(R.string.default_end_time)));
        adapter = new SelectDaysAdapter(days);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> hasModify = true);
        if (!updateChooseWeek()) {
            days.get(0).setChecked(true);
        }
    }

    @OnClick(R2.id.start_time_wrapper)
    public void onStartTimeWrapperClicked() {
        showModifyStartTimePop();
    }

    @OnClick(R2.id.end_time_wrapper)
    public void onEndTimeWrapperClicked() {
        showModifyEndTimePop();
    }

    @Override
    protected void onTitleRightClick() {
        if (!updateChooseWeek()) {
            ToastUtils.showShort("至少选中一天");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.START_TIME, tvStartTime.getText().toString());
        bundle.putString(Keys.END_TIME, tvEndTime.getText().toString());
        bundle.putInt(Keys.WHICH_TIME, NumberUtil.decodeBinary(WEEK));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean updateChooseWeek() {
        int count = 0;
        List<Daybean> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            WEEK[i] = data.get(i).isChecked();
            if (data.get(i).isChecked()) {
                count++;
            }
        }
        TLog.i("startTime", Arrays.toString(WEEK));
        return count > 0;
    }

    private void showModifyStartTimePop() {
        if (startTimePop == null) {
            startTimePop = new TimePickerPop(this);
            startTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    dismiss(startTimePop);
                }

                @Override
                public void onChoose(String time, String hour, String minute) {
                }

                @Override
                public void onSelected(String time, String hour, String minute) {
                    dismiss(startTimePop);
                    if (timeMax(hour,minute,endTime)){
                        ToastUtils.showShort(R.string.start_time_must_before_end_time);
                    }else {
                        hasModify = true;
                        startTime = time;
                        tvStartTime.setText(time);
                    }
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        backgroundAlpha(0.8f);
        if (Check.hasContent(startTime)) {
            startTimePop.setDefaultTime(startTime.split(":")[0], startTime.split(":")[1]);
        }
        startTimePop.show(getWindow().getDecorView());
    }

    private void showModifyEndTimePop() {
        if (endTimePop == null) {
            endTimePop = new TimePickerPop(this);
            endTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    dismiss(endTimePop);
                }

                @Override
                public void onChoose(String time, String hour, String minute) {
                }

                @Override
                public void onSelected(String time, String hour, String minute) {
                    dismiss(endTimePop);
                    if (timeMax(hour, minute, startTime)) {
                        hasModify = true;
                        endTime = time;
                        tvEndTime.setText(time);
                    } else {
                        ToastUtils.showShort(R.string.end_time_must_later_than_start_time);
                    }
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        backgroundAlpha(0.8f);
        if (Check.hasContent(endTime)) {
            endTimePop.setDefaultTime(endTime.split(":")[0], endTime.split(":")[1]);
        }
        endTimePop.show(getWindow().getDecorView());
    }

    @Override
    protected void onTitleLeftClick() {
        if (hasModify) {
            showUnsavedDialog();
        } else {
            super.onTitleLeftClick();
        }
    }

    private void showUnsavedDialog() {
        if (unsavedDialog == null) {
            unsavedDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.tips)
                    .content("您有修改的配置尚未保存，是否立即保存？")
                    .left("放弃")
                    .right("保存")
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            hasModify = false;
                            onTitleLeftClick();
                        }

                        @Override
                        public void onClickRight(View view) {
                            onTitleRightClick();
                        }
                    });
        }
        unsavedDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismiss(endTimePop, startTimePop, unsavedDialog);
        super.onDestroy();
    }

    private boolean timeMax(String hour, String minute, String time) {
        int h1 = Integer.valueOf(hour);
        int m1 = Integer.valueOf(minute);
        int h2 = 0, m2 = 0;
        if (time != null && time.contains(":")) {
            String[] split = time.split(":");
            h2 = Integer.valueOf(split[0]);
            m2 = Integer.valueOf(split[1]);
        }
        return h1 > h2 || (h1 == h2 && m1 > m2);
    }
}
