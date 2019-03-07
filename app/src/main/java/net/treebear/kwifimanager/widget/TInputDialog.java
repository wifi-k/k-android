package net.treebear.kwifimanager.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.util.DensityUtil;

public class TInputDialog {
    private Context mContext;
    private Dialog mDialog;
    private View view;

    private double widthPercent = 0.8d;
    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    private EditText etInput;
    private InputDialogListener mListener = new InputDialogListener() {
        @Override
        public void onLeftClick(String s) {

        }

        @Override
        public void onRightClick(String s) {

        }
    };

    public TInputDialog(@NonNull Context context) {
        mContext = context;
        initDialog();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void show(){
        if (mDialog == null){
            initDialog();
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss(){
        if (mDialog!=null){
            mDialog.dismiss();
        }
    }

    private void initDialog() {
        mDialog = new Dialog(mContext);
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_input, null);
        findView();
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(view);
            //解决dialog中EditText无法弹出输入的问题
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams p = window.getAttributes();
            p.width = (int) (DensityUtil.getScreenWidth(mContext) * widthPercent);
            window.setAttributes(p);
        }
    }

    private void findView() {
        tvTitle = view.findViewById(R.id.tv_title);
        etInput = view.findViewById(R.id.et_input);
        tvLeft = view.findViewById(R.id.btn_left);
        tvRight = view.findViewById(R.id.btn_right);
        //弹出对话框后直接弹出键盘
        etInput.requestFocus();
        etInput.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mListener.onTextChanged(s.toString());
            }
        });
    }

    public void setTitle(@StringRes int title){
        tvTitle.setText(title);
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setInputDialogListener(InputDialogListener listener) {
        mListener = listener;
        tvLeft.setOnClickListener(v -> mListener.onLeftClick(etInput.getText().toString()));
        tvRight.setOnClickListener(v -> mListener.onRightClick(etInput.getText().toString()));
    }

    public static abstract class InputDialogListener {
        public void onTextChanged(String s) {
        }

        public abstract void onLeftClick(String s);

        public abstract void onRightClick(String s);

    }

}
