package net.treebear.kwifimanager.activity.home.time;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.TimeLimitBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.widget.TimePickerPop;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    public int layoutId() {
        return R.layout.activity_modify_time;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            timeLimit = (TimeLimitBean) params.getSerializable(Keys.TIME_LIMIT_BEAN);
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.edit);
        if (timeLimit != null) {
            tvStartTime.setText(timeLimit.getStartTime());
            tvEndTime.setText(timeLimit.getEndTime());
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
        // TODO: 2019/3/11 设置时间

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

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
