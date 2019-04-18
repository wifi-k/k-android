package cn.treebear.kwifimanager.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.karumi.dividers.DividerBuilder;
import com.karumi.dividers.DividerItemDecoration;
import com.karumi.dividers.Layer;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.activity.gallery.FullImageActivity;
import cn.treebear.kwifimanager.adapter.GalleryDisplayAdapter;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.Keys;

public class SelectPictureFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title_text)
    TextView tvTitle;
    private ArrayList<LocalImageBean> imageBeans = new ArrayList<>();
    private ArrayList<LocalImageSection> sections = new ArrayList<>();
    private GalleryDisplayAdapter galleryAdapter;
    private LoaderManager loaderManager;

    @Override
    public int layoutId() {
        return R.layout.fragment_select_picture;
    }

    @Override
    protected void initView() {
        super.initView();
        loaderManager = LoaderManager.getInstance(this);
        setTitle(R.mipmap.back, "已选中1张照片");
        setAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        scanImages();
    }

    public void updateStatus() {
//        if (galleryAdapter != null) {
//            galleryAdapter.notifyDataSetChanged();
//        }
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(new Layer(DividerBuilder.get().with(new ColorDrawable(Color.WHITE)).build())));
        galleryAdapter = new GalleryDisplayAdapter(sections, GalleryHelper.IMAGE_MODEL_SELECT);
        recyclerView.setAdapter(galleryAdapter);
        galleryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            Bundle bundle = new Bundle();

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int realImageIndex = GalleryHelper.getRealImageIndex(position);
                if (realImageIndex != -1) {
                    bundle.clear();
                    bundle.putInt(Keys.POSITION, realImageIndex);
                    startActivity(FullImageActivity.class, bundle);
                }
            }
        });
        galleryAdapter.setOnGallerySelectChangedListener((beans, changed) -> tvTitle.setText(String.format("已选中%s张照片", beans.size())));
    }

    @Override
    protected void onTitleLeftClick() {
        GalleryHelper.clearCheck();
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).hideSelectGalleryFragment();
        }
    }

    @OnClick(R2.id.tv_share_pic)
    public void onTvSharePicClicked() {
        if (checkData()) {
            ToastUtils.showShort(R.string.please_select_unless_one_pic);
            return;
        }
        // TODO: 2019/4/18 goto share
    }

    @OnClick(R2.id.tv_delete_pic)
    public void onTvDeletePicClicked() {
        if (checkData()) {
            ToastUtils.showShort(R.string.please_select_unless_one_pic);
            return;
        }
        // TODO: 2019/4/18 goto delete
    }

    @OnClick(R2.id.tv_backup_pic)
    public void onTvBackupPicClicked() {
        if (checkData()) {
            ToastUtils.showShort(R.string.please_select_unless_one_pic);
            return;
        }
        // TODO: 2019/4/18 goto backup
    }

    @OnClick(R2.id.tv_download_pic)
    public void onTvDownloadPicClicked() {
        if (checkData()) {
            ToastUtils.showShort(R.string.please_select_unless_one_pic);
            return;
        }
        // TODO: 2019/4/18 goto download
    }

    public void clearStatus() {
        tvTitle.setText("已选中1张照片");
        if (galleryAdapter != null) {
            galleryAdapter.clearCheckStatus();
        }
    }

    private boolean checkData() {
        return galleryAdapter != null && galleryAdapter.getCheckResult().size() > 0;
    }

    private void scanImages() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        if (loaderManager.getLoader(0) == null) {
                            loaderManager.initLoader(0, null, SelectPictureFragment.this);
                        } else {
                            loaderManager.restartLoader(0, null, SelectPictureFragment.this);
                        }
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("您拒绝了存储权限，无法管理相册文件");
                    }
                }).request();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return GalleryHelper.onCreateLoader();
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        imageBeans.clear();
        sections.clear();
        GalleryHelper.onLoadFinished(data);
        imageBeans.addAll(GalleryHelper.getImageBeans());
        sections.addAll(GalleryHelper.getSections());
        galleryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    @Override
    public void onDestroy() {
        loaderManager.destroyLoader(0);
        super.onDestroy();
    }
}
