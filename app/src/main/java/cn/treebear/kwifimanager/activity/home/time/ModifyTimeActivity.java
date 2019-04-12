package cn.treebear.kwifimanager.activity.home.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.HealthyModelBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;
import cn.treebear.kwifimanager.widget.pop.TimePickerPop;

/**
 * @author Administrator
 */
public class ModifyTimeActivity extends BaseActivity {

    @BindView(R2.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R2.id.tv_end_time)
    TextView tvEndTime;
    private HealthyModelBean.WifiBean.TimerBean timeLimit;
    private TimePickerPop startTimePop;
    private TimePickerPop endTimePop;
    private int position = -1;
    private TMessageDialog unsavedDialog;
    private boolean hasModify;

    @Override
    public int layoutId() {
        return R.layout.activity_modify_time;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            timeLimit = (HealthyModelBean.WifiBean.TimerBean) params.getSerializable(Keys.TIME_LIMIT_BEAN);
            position = params.getInt(Keys.POSITION, -1);
        }
    }

    @Override
    protected void initView() {
        if (timeLimit != null) {
            setTitleBack(R.string.edit,R.string.save);
            tvStartTime.setText(timeLimit.getStartTime());
            tvEndTime.setText(timeLimit.getEndTime());
        } else {
            setTitleBack(R.string.increase,R.string.save);
        }
    }

    @OnClick(R2.id.start_time_wrapper)
    public void onStartTimeWrapperClicked() {
        showStartTimePop();
    }

    @OnClick(R2.id.end_time_wrapper)
    public void onEndTimeWrapperClicked() {
        showEndTimePop();
    }

    @Override
    protected void onTitleRightClick() {
        Intent intent = new Intent();
        intent.putExtra(Keys.POSITION, position);
        intent.putExtra(Keys.IT_START_TIME, tvStartTime.getText().toString());
        intent.putExtra(Keys.IT_END_TIME, tvEndTime.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R2.id.btn_confirm)
    public void onBtnConfirmClicked() {
        Intent intent = new Intent();
        intent.putExtra(Keys.POSITION, position);
        intent.putExtra(Keys.IT_START_TIME, tvStartTime.getText().toString());
        intent.putExtra(Keys.IT_END_TIME, tvEndTime.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showStartTimePop() {
        if (startTimePop == null) {
            startTimePop = new TimePickerPop(this);
            startTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    startTimePop.dismiss();
                }

                @Override
                public void onChoose(String time, String hour, String minute) {

                }

                @Override
                public void onSelected(String time, String hour, String minute) {
                    hasModify = true;
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
        if (timeLimit != null && Check.hasContent(timeLimit.getStartTime())) {
            startTimePop.setDefaultTime(timeLimit.getStartTime().split(":")[0], timeLimit.getStartTime().split(":")[1]);
        }
        startTimePop.show(getWindow().getDecorView());
    }

    private void showEndTimePop() {
        if (endTimePop == null) {
            endTimePop = new TimePickerPop(this);
            endTimePop.addTimeSelectListener(new TimePickerPop.OnTimeSelectListener() {
                @Override
                public void onCancelClick() {
                    endTimePop.dismiss();
                }

                @Override
                public void onChoose(String time, String hour, String minute) {

                }

                @Override
                public void onSelected(String time, String hour, String minute) {
                    hasModify = true;
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
        if (timeLimit != null && Check.hasContent(timeLimit.getEndTime())) {
            endTimePop.setDefaultTime(timeLimit.getEndTime().split(":")[0], timeLimit.getEndTime().split(":")[1]);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                            onBtnConfirmClicked();
                        }
                    });
        }
        unsavedDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismiss(endTimePop, startTimePop);
        dismiss(unsavedDialog);
        super.onDestroy();
    }
}
