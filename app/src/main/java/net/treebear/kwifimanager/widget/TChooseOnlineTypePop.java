package net.treebear.kwifimanager.widget;

import android.content.Context;
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
public class TChooseOnlineTypePop {

    private final Context mContext;
    private PopupWindow popupWindow;
    private View mContentView;
    private OnChooseTypeListener mListener = new OnChooseTypeListener() {
        @Override
        public void onClickAutoIp() {

        }

        @Override
        public void onClickStaticIp() {

        }

        @Override
        public void onClickPPPOE() {

        }

        @Override
        public void onDismiss() {

        }
    };
    private TextView tvDynamic;
    private TextView tvStatic;
    private TextView tvPppoe;

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
        tvDynamic.setOnClickListener(v -> {
            mListener.onClickAutoIp();
            popupWindow.dismiss();
        });
        tvStatic.setOnClickListener(v -> {
            mListener.onClickStaticIp();
            popupWindow.dismiss();
        });
        tvPppoe.setOnClickListener(v -> {
            mListener.onClickPPPOE();
            popupWindow.dismiss();
        });

    }

    public void show(View parent) {
        if (popupWindow == null) {
            initPopupWindow();
        }
        popupWindow.showAsDropDown(parent, Gravity.BOTTOM, DensityUtil.dip2px(mContext,20), Gravity.END);
    }

    public void setOnChooseTypeList(OnChooseTypeListener listener) {
        this.mListener = listener;
    }

    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public static abstract class OnChooseTypeListener {
        public abstract void onClickAutoIp();

        public abstract void onClickStaticIp();

        public abstract void onClickPPPOE();

        public void onDismiss() {
        }
    }

}
