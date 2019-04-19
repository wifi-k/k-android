package cn.treebear.kwifimanager.activity.gallery;

import android.os.Bundle;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.FullImageAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.util.DateTimeUtils;
import cn.treebear.kwifimanager.util.UMShareUtils;
import cn.treebear.kwifimanager.util.UserInfoUtil;
import cn.treebear.kwifimanager.widget.pop.ShareGalleryPop;

public class FullImageActivity extends BaseActivity {

    @BindView(R2.id.root_view)
    ConstraintLayout rootView;
    @BindView(R2.id.recycler_view)
    RecyclerViewPager recyclerView;
    @BindView(R2.id.tv_title_text)
    TextView tvTitle;
    @BindView(R2.id.iv_share_pic)
    TextView ivSharePic;
    @BindView(R2.id.iv_delete_pic)
    TextView ivDeletePic;
    @BindView(R2.id.iv_backup_pic)
    TextView ivBackupPic;
    @BindView(R2.id.iv_download_pic)
    TextView ivDownloadPic;
    private int imagePosition;
    private FullImageAdapter adapter;
    private ShareGalleryPop shareGalleryPop;

    @Override
    public int layoutId() {
        return R.layout.activity_full_image;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            imagePosition = params.getInt(Keys.POSITION, 0);
        }
    }

    @Override
    protected void initView() {
        statusTransparentFontWhite();
        setTitle(R.mipmap.ic_line_arrow_left_white, GalleryHelper.getImageBeans().get(imagePosition).getDate(), "", 0, false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);
        adapter = new FullImageAdapter(GalleryHelper.getImageBeans());
        if (imagePosition >= GalleryHelper.getImageBeans().size()) {
            manager.scrollToPosition(GalleryHelper.getImageBeans().size() - 1);
        } else {
            manager.scrollToPosition(imagePosition);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.addOnPageChangedListener((i, i1) -> {
            if (i1 >= GalleryHelper.getImageBeans().size()) {
                return;
            }
            imagePosition = i1;
//            tvTitle.setText(GalleryHelper.getImageBeans().get(i1).getDate());
            tvTitle.setText(DateTimeUtils.formatYMDHm4Gallery(GalleryHelper.getImageBeans().get(i1).getDateAdded()));
        });
    }

    @OnClick(R2.id.iv_share_pic)
    public void onIvSharePicClicked() {
        if (shareGalleryPop == null) {
            shareGalleryPop = new ShareGalleryPop(this);
            shareGalleryPop.setListener(new ShareGalleryPop.DoClickListener() {
                @Override
                public void onSelectAlbum(int position) {
                }

                @Override
                public void onClickWechat() {
                    dismiss(shareGalleryPop);
                    UMShareUtils.shareWxImage(FullImageActivity.this, "小K云管家", String.format("%s分享了照片", UserInfoUtil.getUserInfo().getName()),
                            GalleryHelper.getImageBeans().get(imagePosition).getFilepath(), null);
                }

                @Override
                public void onCancel() {
                    dismiss(shareGalleryPop);
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        shareGalleryPop.show(rootView);
        backgroundAlpha(0.8f);
    }

    @OnClick(R2.id.iv_delete_pic)
    public void onIvDeletePicClicked() {
    }

    @OnClick(R2.id.iv_backup_pic)
    public void onIvBackupPicClicked() {
    }

    @OnClick(R2.id.iv_download_pic)
    public void onIvDownloadPicClicked() {
    }

}
