package cn.treebear.kwifimanager.activity.home.time;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.TimeLimitBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.widget.pop.TimePickerPop;

/**
 * @author Administrator
 */
public class ModifyTimeActivity extends BaseActivity {

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    private TimeLimitBean timeLimit;
    private TimePickerPop startTimePop;
    private TimePickerPop endTimePop;
    private int position = -1;

    @Override
    public int layoutId() {
        return R.layout.activity_modify_time;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            timeLimit = (TimeLimitBean) params.getSerializable(Keys.TIME_LIMIT_BEAN);
            position = params.getInt(Keys.POSITION, -1);
        }
    }

    @Override
    protected void initView() {
        if (timeLimit != null) {
            setTitleBack(R.string.edit);
            tvStartTime.setText(timeLimit.getStartTime());
            tvEndTime.setText(timeLimit.getEndTime());
        } else {
            setTitleBack(R.string.increase);
        }
    }

    @OnClick(R.id.start_time_wrapper)
    public void onStartTimeWrapperClicked() {
        initStartTimePop();
        backgroundAlpha(0.8f);
        startTimePop.show(getWindow().getDecorView());
    }

    @OnClick(R.id.end_time_wrapper)
    public void onEndTimeWrapperClicked() {
        initEndTimePop();
        backgroundAlpha(0.8f);
        endTimePop.show(getWindow().getDecorView());
    }

    @OnClick(R.id.btn_confirm)
    public void onBtnConfirmClicked() {
        Intent intent = new Intent();
        intent.putExtra(Keys.POSITION, position);
        intent.putExtra(Keys.START_TIME, tvStartTime.getText().toString());
        intent.putExtra(Keys.END_TIME, tvEndTime.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initStartTimePop() {
        if (startTimePop == null) {
            startTimePop = new TimePickerPop(this);
            startTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    startTimePop.dismiss();
                }

                @Override
                public void onSelected(String time) {
                    tvStartTime.setText(time);
                    startTimePop.dismiss();
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
    }

    private void initEndTimePop() {
        if (endTimePop == null) {
            endTimePop = new TimePickerPop(this);
            endTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    endTimePop.dismiss();
                }

                @Override
                public void onSelected(String time) {
                    tvEndTime.setText(time);
                    endTimePop.dismiss();
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        dismiss(endTimePop, startTimePop);
        super.onDestroy();
    }
}
