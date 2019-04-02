package cn.treebear.kwifimanager.widget.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.Dismissable;

/**
 * @author Administrator
 */
public class TChoosePicTypePop implements Dismissable {

    private final Context mContext;
    private PopupWindow popupWindow;
    private View mContentView;
    private TextView tvCancel;
    private TextView tvLeft;
    private TextView tvRight;
    private @DrawableRes
    int leftDrawableRes = R.mipmap.ic_take_picture_camera;
    private @DrawableRes
    int rightDrawableRes = R.mipmap.ic_take_picture_photo;
    private OnChooseTypeListener mListener = new OnChooseTypeListener() {

        @Override
        public void onCancelClick() {

        }

        @Override
        public void onLeftClick() {

        }

        @Override
        public void onRightClick() {

        }

        @Override
        public void onDismiss() {

        }
    };

    public TChoosePicTypePop(Context context) {
        mContext = context;
    }

    private void initPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            //打气
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_take_picture, null);
            initView();
            //设置View
            popupWindow.setContentView(mContentView);
            //设置宽与高
            popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
//            popupWindow.setHeight((int) (DensityUtil.getScreenHeight() / 2.2d));
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
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
        tvLeft = mContentView.findViewById(R.id.tv_left);
        tvRight = mContentView.findViewById(R.id.tv_right);
        tvCancel = mContentView.findViewById(R.id.tv_cancel);
        tvLeft.setOnClickListener(v -> mListener.onLeftClick());
        tvRight.setOnClickListener(v -> mListener.onRightClick());
        tvCancel.setOnClickListener(v -> mListener.onCancelClick());
    }

    public void show(View parent) {
        if (popupWindow == null) {
            initPopupWindow();
        }
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
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

    public interface OnChooseTypeListener {
        /**
         * 取消选中
         */
        void onCancelClick();

        /**
         * 左侧选中
         */
        void onLeftClick();

        /**
         * 右侧选中
         */
        void onRightClick();

        void onDismiss();
    }

}
