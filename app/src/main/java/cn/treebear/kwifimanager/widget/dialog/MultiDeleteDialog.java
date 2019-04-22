package cn.treebear.kwifimanager.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.util.DensityUtil;
import cn.treebear.kwifimanager.widget.Dismissable;

public class MultiDeleteDialog implements Dismissable {

    private final Context mContext;
    private Dialog mDialog;
    private View mContentView;
    private MultiDeleteListener mListener = new MultiDeleteListener() {
        @Override
        public void onClickCancel() {
            dismiss();
        }

        @Override
        public void onClickConfirm(boolean first, boolean second) {
            dismiss();
        }
    };
    private TextView tvTitle;
    private CheckBox cbDeleteFromPhone;
    private CheckBox cbDeleteFromDesk;
    private TextView tvCancel;
    private TextView tvConfirm;


    public MultiDeleteDialog(Context context) {
        mContext = context;
        initDialog();
    }

    private void initDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(mContext);
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_multi_delete, null);
            findView();
            mDialog.setCancelable(false);
            mDialog.setContentView(mContentView);
            mDialog.setCanceledOnTouchOutside(false);
            Window window = mDialog.getWindow();
            if (window != null) {
                window.setContentView(mContentView);
                window.setBackgroundDrawable(new ColorDrawable(0));
                WindowManager.LayoutParams p = window.getAttributes();
                p.width = (int) (DensityUtil.getScreenWidth(mContext) * 0.8);
                window.setAttributes(p);
            }
        }
    }

    private void findView() {
        tvTitle = mContentView.findViewById(R.id.tv_title);
        cbDeleteFromPhone = mContentView.findViewById(R.id.cb_delete_from_phone);
        cbDeleteFromDesk = mContentView.findViewById(R.id.cb_delete_from_desk);
        tvCancel = mContentView.findViewById(R.id.tv_left);
        tvConfirm = mContentView.findViewById(R.id.tv_right);
        setListeners();
    }

    private void setListeners() {
        tvCancel.setOnClickListener(v -> mListener.onClickCancel());
        tvConfirm.setOnClickListener(v -> mListener.onClickConfirm(cbDeleteFromPhone.isChecked(), cbDeleteFromDesk.isChecked()));
    }

    public void setMultiDeleteListener(MultiDeleteListener listener) {
        mListener = listener;
        setListeners();
    }

    public void clearCheck() {
        if (cbDeleteFromPhone != null) {
            cbDeleteFromPhone.setChecked(false);
        }
        if (cbDeleteFromDesk != null) {
            cbDeleteFromDesk.setChecked(false);
        }
    }

    public void show() {
        if (mDialog == null) {
            initDialog();
        }
        mDialog.show();
    }

    @Override
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }
}
