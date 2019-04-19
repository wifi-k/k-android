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

import java.util.ArrayList;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.ShareAlbumAdapter;
import cn.treebear.kwifimanager.bean.local.AlbumBean;
import cn.treebear.kwifimanager.widget.Dismissable;

public class ShareGalleryPop implements Dismissable {

    private Context mContext;
    private PopupWindow popupWindow;
    private View mContentView;
    private ConstraintLayout shareGalleryWrapper;
    private RecyclerView recyclerView;
    private TextView tvShareWeixin;
    private TextView tvCancel;
    private ArrayList<AlbumBean> shareAlbums = new ArrayList<>();
    private DoClickListener mListener = new DoClickListener() {
        @Override
        public void onSelectAlbum(int position) {

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
    private ShareAlbumAdapter shareAlbumAdapter;

    public ShareGalleryPop(Context mContext) {
        this.mContext = mContext;
    }

    public void setShareAlbums(ArrayList<AlbumBean> albums) {
        shareAlbums.clear();
        shareAlbums.addAll(albums);
        shareAlbumAdapter.notifyDataSetChanged();
        shareGalleryWrapper.setVisibility(shareAlbums.size() == 0 ? View.GONE : View.VISIBLE);
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
        shareGalleryWrapper.setVisibility(shareAlbums.size() == 0 ? View.GONE : View.VISIBLE);
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
        shareAlbumAdapter = new ShareAlbumAdapter(shareAlbums);
        recyclerView.setAdapter(shareAlbumAdapter);
        shareAlbumAdapter.setOnItemClickListener((adapter, view, position) -> mListener.onSelectAlbum(position));
        tvCancel.setOnClickListener(v -> mListener.onCancel());
        tvShareWeixin.setOnClickListener(v -> mListener.onClickWechat());
    }

    @Override
    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public interface DoClickListener {
        void onSelectAlbum(int position);

        void onClickWechat();

        void onCancel();

        void onDismiss();
    }
}
