package cn.treebear.kwifimanager.activity.gallery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.widget.TextView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.FullImageAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.Keys;

public class FullImageActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerViewPager recyclerView;
    @BindView(R.id.tv_title_text)
    TextView tvTitle;
    private int imagePosition;
    private FullImageAdapter adapter;

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
        manager.scrollToPosition(imagePosition);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i, int i1) {
                if (i1 >= GalleryHelper.getImageBeans().size()) {
                    return;
                }
                tvTitle.setText(GalleryHelper.getImageBeans().get(i1).getDate());
            }
        });
    }

    @OnClick(R.id.iv_share_pic)
    public void onIvSharePicClicked() {
    }

    @OnClick(R.id.iv_delete_pic)
    public void onIvDeletePicClicked() {
    }

    @OnClick(R.id.iv_backup_pic)
    public void onIvBackupPicClicked() {
    }
}
