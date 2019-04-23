package cn.treebear.kwifimanager.activity.gallery;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.karumi.dividers.DividerBuilder;
import com.karumi.dividers.DividerItemDecoration;
import com.karumi.dividers.Layer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.adapter.GalleryDisplayAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.fragment.GalleryFragment;
import cn.treebear.kwifimanager.fragment.SelectPictureFragment;
import cn.treebear.kwifimanager.test.BeanTest;
import cn.treebear.kwifimanager.util.Check;

public class SelectPictureActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R2.id.tv_bottom_button)
    TextView tvBottomButton;
    @BindView(R2.id.tv_empty_view)
    TextView tvTips;
    @BindView(R.id.tv_title_text)
    TextView tvTitle;
    private String confirmButtonText = "确定";
    private ArrayList<LocalImageBean> imageBeans = new ArrayList<>();
    private ArrayList<LocalImageSection> sections = new ArrayList<>();
    private LoaderManager loaderManager;
    private GalleryDisplayAdapter galleryAdapter;
    @Override
    public int layoutId() {
        return R.layout.layout_title_recycler_botton;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            confirmButtonText = params.getString(Keys.CONFIRM_BUTTON_TEXT);
        }
    }

    @Override
    protected void initView() {
        tvBottomButton.setText(confirmButtonText);
        loaderManager = LoaderManager.getInstance(this);
        setAdapter();
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort(R.string.refuse_file_permission_cannot_manage_gallery);
                    }
                }).request();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtils.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            scanImages();
        } else {
            tvTips.setText(R.string.no_file_permission_click_obtain);
            tvTips.setVisibility(View.VISIBLE);
        }
    }

    private void scanImages() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        if (loaderManager.getLoader(0) == null) {
                            loaderManager.initLoader(0, null, SelectPictureActivity.this);
                        } else {
                            loaderManager.restartLoader(0, null, SelectPictureActivity.this);
                        }
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("您拒绝了存储权限，无法管理相册文件");
                    }
                }).request();
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
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
                    BeanTest.testUpload(GalleryHelper.getSections().get(position).t.getFilepath());
                    bundle.putInt(Keys.POSITION, realImageIndex);
                    startActivity(FullImageActivity.class, bundle);
                }
            }
        });
        galleryAdapter.setOnGallerySelectChangedListener((beans, changed) -> tvTitle.setText(String.format("已选中%s张照片", beans.size())));
    }

    @OnClick(R2.id.tv_tips)
    public void onTvTipsClick() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        scanImages();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort(R.string.refuse_file_permission_cannot_manage_gallery);
                    }
                }).request();
    }

    @OnClick(R2.id.tv_bottom_button)
    public void onViewClicked() {
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return GalleryHelper.onCreateLoader();
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        imageBeans.clear();
        sections.clear();
        GalleryHelper.onLoadFinished(cursor);
        imageBeans.addAll(GalleryHelper.getImageBeans());
        sections.addAll(GalleryHelper.getSections());
        galleryAdapter.notifyDataSetChanged();
        if (!Check.hasContent(GalleryHelper.getImageBeans())) {
            tvTips.setText(R.string.current_no_picture);
            tvTips.setVisibility(View.VISIBLE);
        } else {
            tvTips.setVisibility(View.GONE);
        }
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
