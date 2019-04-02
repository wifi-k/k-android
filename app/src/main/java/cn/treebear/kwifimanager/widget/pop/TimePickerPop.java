package cn.treebear.kwifimanager.widget.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.util.DensityUtil;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.Dismissable;

public class TimePickerPop implements Dismissable {

    private final Context mContext;
    private PopupWindow popupWindow;
    private View mContentView;
    private com.contrarywind.view.WheelView wheelHour;
    private com.contrarywind.view.WheelView wheelMinute;
    private TextView tvCancel;
    private TextView tvConfirm;
    private ArrayList<String> hours;
    private ArrayList<String> minutes;
    private String mCurrentHour = "12";
    private String mCurrentMinute = "30";
    private OnTimeSelectListener mTimeSelectListener = new OnTimeSelectListener() {
        @Override
        public void onCancelClick() {

        }

        @Override
        public void onChoose(String time) {

        }

        @Override
        public void onSelected(String time) {

        }

        @Override
        public void onDismiss() {

        }
    };
    private TextView tvTitle;

    public TimePickerPop(Context context) {
        mContext = context;
        initDefaultData();
        initPopupWindow();
    }

    private void initDefaultData() {
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add(i, String.valueOf(i < 10 ? "0" + i : i));
        }
        for (int i = 0; i < 60; i++) {
            minutes.add(i, String.valueOf(i < 10 ? "0" + i : i));
        }
    }

    private void initPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            //打气
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_time_selector, null);
            initView();
            //设置View
            popupWindow.setContentView(mContentView);
            //设置宽与高
            popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight((int) (DensityUtil.getScreenHeight() / 2.2d));
//            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.anim.pickerview_slide_in_bottom);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
        }

    }

    private void initView() {
        if (mContentView == null) {
            TLog.e("mContentView == null，无法创建Pop");
            return;
        }
        wheelHour = mContentView.findViewById(R.id.wv_time_hour);
        wheelMinute = mContentView.findViewById(R.id.wv_time_minute);
        tvCancel = mContentView.findViewById(R.id.tv_cancel);
        tvConfirm = mContentView.findViewById(R.id.tv_confirm);
        tvTitle = mContentView.findViewById(R.id.tv_pop_title);
        wheelHour.setTextColorCenter(Config.Colors.MAIN);
        wheelHour.setTextColorOut(Config.Colors.COLOR_4A5A78);
        wheelMinute.setTextColorCenter(Config.Colors.MAIN);
        wheelMinute.setTextColorOut(Config.Colors.COLOR_4A5A78);
        wheelHour.setTextSize(20);
        wheelMinute.setTextSize(20);
//        wheelHour.setBackgroundColor(Config.Colors.COLOR_D8);
//        wheelMinute.setBackgroundColor(Config.Colors.COLOR_D8);
        wheelHour.setCurrentItem(12);
        wheelMinute.setCurrentItem(30);
        wheelHour.setDividerColor(Config.Colors.COLOR_C0);
        wheelMinute.setDividerColor(Config.Colors.COLOR_C0);
        wheelHour.setDividerType(WheelView.DividerType.FILL);
        wheelMinute.setDividerType(WheelView.DividerType.FILL);
//        wheelHour.setItems(hours);
        wheelHour.setAdapter(new ArrayWheelAdapter<String>(hours));
//        wheelMinute.setItems(minutes);
        wheelMinute.setAdapter(new ArrayWheelAdapter<String>(minutes));
        wheelHour.setOnItemSelectedListener(index -> {
            mCurrentHour = String.format("%s", hours.get(index));
            mTimeSelectListener.onChoose(String.format("%s:%s",
                    hours.get(wheelHour.getCurrentItem()),
                    minutes.get(wheelMinute.getCurrentItem())
            ));
        });
        wheelMinute.setOnItemSelectedListener(index -> {
            mCurrentMinute = String.format("%s", minutes.get(index));
            mTimeSelectListener.onChoose(String.format("%s:%s",
                    hours.get(wheelHour.getCurrentItem()),
                    minutes.get(wheelMinute.getCurrentItem())
            ));
        });
    }

    public void setTitleText(@StringRes int textId) {
        tvTitle.setText(textId);
    }

    public void addTimeSelectListener(OnTimeSelectListener listener) {
        mTimeSelectListener = listener;
        if (mContentView != null) {
            tvConfirm.setOnClickListener(v -> tvConfirm.postDelayed(() -> mTimeSelectListener.onSelected(String.format("%s:%s",
                    hours.get(wheelHour.getCurrentItem()), minutes.get(wheelMinute.getCurrentItem()))),
                    200));
            tvCancel.setOnClickListener(v -> mTimeSelectListener.onCancelClick());
            popupWindow.setOnDismissListener(() -> mTimeSelectListener.onDismiss());
        }
    }

    public void show(View parent) {
        if (popupWindow == null) {
            initPopupWindow();
        }
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public interface OnTimeSelectListener {
        void onCancelClick();

        void onChoose(String time);

        /**
         * 确认选中数据
         */
        void onSelected(String time);

        void onDismiss();
    }

}
