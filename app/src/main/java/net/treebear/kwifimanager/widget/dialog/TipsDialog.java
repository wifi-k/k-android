package net.treebear.kwifimanager.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.util.DensityUtil;


/**
 * 普通信息提示框
 * 定制化多按键
 * 包含水平进度条，默认不展示
 */
public class TipsDialog implements TDialog {

    private Context mContext;
    private Dialog mDialog;
    private View view;
    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvLeft;
    private TextView tvRight;
    private LinearLayout llButton;
    private static final String TITLE_DEFAULT_COLOR = "#333333";
    private static final String CONTENT_DEFAULT_COLOR = "#999999";
    private static final String CANCEL_DEFAULT_COLOR = "#212121";
    private static final String CONFIRM_DEFAULT_COLOR = "#FFFFFF";
    private float widthPercent = 0.8f;
    private DoClickListener mListener = new DoClickListener();

    public TipsDialog(@NonNull Context context) {
        mContext = context;
        initDialog();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    private void initDialog() {
        mDialog = new Dialog(mContext);
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_tips, null);
        findView();
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(view);
            window.setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams p = window.getAttributes();
            p.width = (int) (DensityUtil.getScreenWidth(mContext) * widthPercent);
            window.setAttributes(p);
        }
    }

    private void findView() {
        ivIcon = view.findViewById(R.id.iv_tips_icon);
        tvTitle = view.findViewById(R.id.tv_title);
        tvContent = view.findViewById(R.id.tv_tips_content);
        tvLeft = view.findViewById(R.id.tv_left);
        tvRight = view.findViewById(R.id.tv_right);
        llButton = view.findViewById(R.id.btn_wrapper);
    }

    public TipsDialog doClick(DoClickListener listener) {
        if (listener != null) {
            mListener = listener;
        }
        ivIcon.setOnClickListener(v -> mListener.onClickIcon(ivIcon));
        tvTitle.setOnClickListener(v -> mListener.onClickTitle(tvTitle));
        tvContent.setOnClickListener(v -> mListener.onClickContent(tvContent));
        tvLeft.setOnClickListener(v -> mListener.onClickLeft(tvLeft));
        tvRight.setOnClickListener(v -> mListener.onClickRight(tvRight));
        return this;
    }

    private CharSequence getString(@StringRes int textRes) {
        return mContext.getResources().getText(textRes);
    }

    private int getColor(int colorRes) {
        return mContext.getResources().getColor(colorRes);
    }

    public TipsDialog icon(@DrawableRes int icon) {
        ivIcon.setImageResource(icon);
        ivIcon.setOnClickListener(v -> mListener.onClickIcon(ivIcon));
        return this;
    }

    public TipsDialog icon(Bitmap icon) {
        ivIcon.setImageBitmap(icon);
        ivIcon.setOnClickListener(v -> mListener.onClickIcon(ivIcon));
        return this;
    }

    public TipsDialog icon(Drawable icon) {
        ivIcon.setImageDrawable(icon);
        ivIcon.setOnClickListener(v -> mListener.onClickIcon(ivIcon));
        return this;
    }

    public TipsDialog iconEnable(boolean enable) {
        ivIcon.setVisibility(enable ? View.VISIBLE : View.GONE);
        return this;
    }

    public TipsDialog title(@StringRes int textRes) {
        return title(getString(textRes));
    }

    public TipsDialog title(CharSequence title) {
        return title(title, TITLE_DEFAULT_COLOR);
    }

    public TipsDialog title(CharSequence title, @Size(min = 1) String colorStr) {
        return title(title, Color.parseColor(colorStr));
    }

    public TipsDialog title(CharSequence title, @ColorInt int colorInt) {
        tvTitle.setText(title);
        tvTitle.setTextColor(colorInt);
        tvTitle.setOnClickListener(v -> mListener.onClickTitle(tvTitle));
        return this;
    }

    public TipsDialog noTitle() {
        tvTitle.setVisibility(View.GONE);
        return this;
    }

    public TipsDialog content(@StringRes int textRes) {
        return content(getString(textRes), CONTENT_DEFAULT_COLOR);
    }

    public TipsDialog content(CharSequence content) {
        return content(content, CONTENT_DEFAULT_COLOR);
    }

    public TipsDialog content(CharSequence content, @Size(min = 1) String colorStr) {
        return content(content, Color.parseColor(colorStr));
    }

    public TipsDialog content(CharSequence content, @ColorInt int colorInt) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText(content);
        tvContent.setTextColor(colorInt);
        tvContent.setOnClickListener(v -> mListener.onClickTitle(tvContent));
        return this;
    }

    public TipsDialog noContent() {
        tvContent.setVisibility(View.GONE);
        return this;
    }

    public TipsDialog left(@StringRes int textRes) {
        return left(getString(textRes), Color.parseColor(CANCEL_DEFAULT_COLOR));
    }

    public TipsDialog left(CharSequence text) {
        return left(text, Color.parseColor(CANCEL_DEFAULT_COLOR));
    }

    public TipsDialog left(CharSequence text, @ColorInt int colorInt) {
        return left(text, colorInt, null);
    }

    public TipsDialog left(CharSequence text, @ColorInt int colorInt, DoClickListener listener) {
        if (listener != null) {
            mListener = listener;
        }
        tvLeft.setText(text);
        tvLeft.setTextColor(colorInt);
        tvLeft.setOnClickListener(v -> mListener.onClickLeft(tvLeft));
        return this;
    }

    public TipsDialog right(@StringRes int textRes) {
        return right(getString(textRes), Color.parseColor(CONFIRM_DEFAULT_COLOR));
    }

    public TipsDialog right(@StringRes int textRes, @ColorRes int colorRes) {
        return right(getString(textRes), getColor(colorRes));
    }

    public TipsDialog right(CharSequence text) {
        return right(text, Color.parseColor(CONFIRM_DEFAULT_COLOR));
    }

    public TipsDialog right(CharSequence text, @ColorInt int colorInt) {
        return right(text, colorInt, null);
    }

    public TipsDialog right(CharSequence text, @ColorInt int colorInt, DoClickListener listener) {
        if (listener != null) {
            mListener = listener;
        }
        tvRight.setText(text);
        tvRight.setTextColor(colorInt);
        tvRight.setOnClickListener(v -> mListener.onClickRight(tvRight));
        return this;
    }

    public TipsDialog oneButtonLeft() {
        tvRight.setVisibility(View.GONE);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setBackgroundResource(R.drawable.btn_bottom_lrbr8_gray);
        return this;
    }

    public TipsDialog oneButtonRight() {
        tvRight.setVisibility(View.VISIBLE);
        tvLeft.setVisibility(View.GONE);
        tvRight.setBackgroundResource(R.drawable.btn_bottom_lrbr8_main);
        return this;
    }

    public TipsDialog noButton() {
        llButton.setVisibility(View.GONE);
        return this;
    }

    public TipsDialog widthPercent(float percent) {
        widthPercent = percent;
        return this;
    }

    @Override
    public void show() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void update() {
        show();
    }

    @Override
    public void dismiss() {
        mDialog.dismiss();
    }

    public static class DoClickListener {

        public void onClickTitle(TextView tvTitle) {
        }

        public void onClickIcon(ImageView ivIcon) {
        }

        public void onClickContent(TextView tvContent) {
        }

        public void onClickLeft(TextView tvLeft) {
        }

        public void onClickRight(TextView tvRight) {
        }
    }

}
