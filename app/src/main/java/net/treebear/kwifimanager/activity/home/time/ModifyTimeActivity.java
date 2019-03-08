package net.treebear.kwifimanager.activity.home.time;

import android.os.Bundle;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.TimeLimitBean;
import net.treebear.kwifimanager.config.Keys;

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
        if (timeLimit != null) {
            tvStartTime.setText(timeLimit.getStartTime());
            tvEndTime.setText(timeLimit.getEndTime());
        }
    }

    @OnClick(R.id.start_time_wrapper)
    public void onStartTimeWrapperClicked() {
    }

    @OnClick(R.id.end_time_wrapper)
    public void onEndTimeWrapperClicked() {
    }

    @OnClick(R.id.btn_confirm)
    public void onBtnConfirmClicked() {
    }
}
