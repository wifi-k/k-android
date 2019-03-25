package net.treebear.kwifimanager.widget.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.util.DensityUtil;
import net.treebear.kwifimanager.util.TLog;

/**
 * @author Administrator
 */
public class TChooseOnlineTypePop implements TPop{

    private final Context mContext;
    private PopupWindow popupWindow;
    private View mContentView;
    private TextView tvDynamic;
    private TextView tvStatic;
    private TextView tvPppoe;
    private TextView[] tvs;
    public static final int DYNAMIC = 0;
    public static final int STATIC = 1;
    public static final int PPPOE = 2;
    private int currentPosition = 0;
    private int selectedColor = Color.parseColor("#25DBBD");
    private int unSelectedColor = Color.parseColor("#28354C");
    private OnChooseTypeListener mListener = new OnChooseTypeListener() {

        @Override
        public void onClickItem(int position) {
            TLog.i(position);
        }

        @Override
        public void onDismiss() {

        }
    };

    public TChooseOnlineTypePop(Context context) {
        mContext = context;
    }

    private void initPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            //打气
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_choose_online_type, null);
            initView();
            //设置View
            popupWindow.setContentView(mContentView);
            //设置宽与高
            popupWindow.setWidth(DensityUtil.dip2px(mContext, 156));
            popupWindow.setHeight(DensityUtil.dip2px(mContext, 170));
            popupWindow.setAnimationStyle(R.anim.pickerview_slide_in_bottom);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setOnDismissListener(() -> mListener.onDismiss());
        }
    }

    private void initView() {
        if (mContentView == null) {
            TLog.e("mContentView == null，无法创建Pop");
            return;
        }
        tvDynamic = mContentView.findViewById(R.id.tv_dynamic_ip);
        tvStatic = mContentView.findViewById(R.id.tv_static_ip);
        tvPppoe = mContentView.findViewById(R.id.tv_pppoe);
        tvs = new TextView[]{tvDynamic, tvStatic, tvPppoe};
        tvs[currentPosition].setTextColor(selectedColor);
        tvDynamic.setOnClickListener(v -> onSelectItem(DYNAMIC));
        tvStatic.setOnClickListener(v -> onSelectItem(STATIC));
        tvPppoe.setOnClickListener(v -> onSelectItem(PPPOE));
    }

    private void onSelectItem(final int type) {
        if (currentPosition != type) {
            tvs[currentPosition].setTextColor(unSelectedColor);
        }
        mListener.onClickItem(type);
        currentPosition = type;
        tvs[currentPosition].setTextColor(selectedColor);
        popupWindow.dismiss();
    }

    @Override
    public void show(View parent) {
        if (popupWindow == null) {
            initPopupWindow();
        }
        popupWindow.showAsDropDown(parent, Gravity.BOTTOM, DensityUtil.dip2px(mContext, 20), Gravity.END);
    }

    public void setOnChooseTypeList(OnChooseTypeListener listener) {
        this.mListener = listener;
    }

    @Override
    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public static abstract class OnChooseTypeListener {
        public abstract void onClickItem(int position);

        public void onDismiss() {
        }
    }

}
