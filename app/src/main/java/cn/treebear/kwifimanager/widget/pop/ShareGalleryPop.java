package cn.treebear.kwifimanager.widget.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.widget.Dismissable;

public class ShareGalleryPop implements Dismissable {

    private Context mContext;
    private PopupWindow popupWindow;
    private View mContentView;
    private ConstraintLayout shareGalleryWrapper;
    private RecyclerView recyclerView;
    private TextView tvShareWeixin;
    private TextView tvCancel;
    private DoClickListener mListener = new DoClickListener() {
        @Override
        public void onSelect(int position) {

        }

        @Override
        public void onClickWechat() {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onDismiss() {

        }
    };

    public ShareGalleryPop(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(DoClickListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    public void show(View parent) {
        if (popupWindow == null) {
            initPopupWindow();
        }
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    private void initPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            //打气
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.pop_share_gallery, null);
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
        shareGalleryWrapper = mContentView.findViewById(R.id.share_gallery_wrapper);
        recyclerView = mContentView.findViewById(R.id.recycler_view);
        tvShareWeixin = mContentView.findViewById(R.id.tv_share_weixin);
        tvCancel = mContentView.findViewById(R.id.tv_cancel);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        // TODO: 2019/4/10 设置相册数据
        // TODO: 2019/4/10 设置数据选择监听
        tvCancel.setOnClickListener(v -> mListener.onCancel());
        tvShareWeixin.setOnClickListener(v -> mListener.onClickWechat());
    }

    @Override
    public void dismiss() {

    }

    public interface DoClickListener {
        void onSelect(int position);

        void onClickWechat();

        void onCancel();

        void onDismiss();
    }
}
