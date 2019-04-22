package cn.treebear.kwifimanager.fragment;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import cn.treebear.kwifimanager.activity.gallery.FullImageActivity;
import cn.treebear.kwifimanager.activity.gallery.GalleryBackupActivity;
import cn.treebear.kwifimanager.adapter.GalleryDisplayAdapter;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DensityUtil;

/**
 * @author Administrator
 */
public class GalleryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_ai_gallery)
    TextView tvAiGallery;
    @BindView(R2.id.tv_all_gallery)
    TextView tvAllGallery;
    @BindView(R2.id.tv_share_gallery)
    TextView tvShareGallery;
    @BindView(R2.id.rl_tab_wrapper)
    LinearLayout rlTabWrapper;
    @BindView(R2.id.iv_backup)
    ImageView ivBackup;
    @BindView(R2.id.tv_tips)
    TextView tvTips;
    LinearLayout llPicWrapper;
    LinearLayout llTextWrapper;
    private ArrayList<LocalImageBean> imageBeans = new ArrayList<>();
    private ArrayList<LocalImageSection> sections = new ArrayList<>();
    private LoaderManager loaderManager;
    private GalleryDisplayAdapter galleryAdapter;
    private View header;
    private ImageView ivNewerPic;
    private TextView tvNewerPic;
    private ImageView ivSmartPic;
    private TextView tvSmartPic;
    private ImageView ivSharePic;
    private TextView tvSharePic;
    private TextView tvHasNotBackup;
    private TextView tvToBackup;
    private View backupWrapper;

    @Override
    public int layoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initView() {
        loaderManager = LoaderManager.getInstance(this);
        setAdapter();
        listenScroll();
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

    private void setAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(new Layer(DividerBuilder.get().with(new ColorDrawable(Color.WHITE)).build())));
        galleryAdapter = new GalleryDisplayAdapter(sections);
        header = LayoutInflater.from(mContext).inflate(R.layout.header_gallery_list, mRootView, false);
        findHeaderView();
        galleryAdapter.addHeaderView(header);
        recyclerView.setAdapter(galleryAdapter);
        rlTabWrapper.setEnabled(false);
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
        galleryAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if (!GalleryHelper.getSections().get(position).isHeader) {
                GalleryHelper.appendCheckImage(GalleryHelper.getSections().get(position).t, true);
                if (mContext instanceof MainActivity) {
                    ((MainActivity) mContext).showSelectGalleryFragment();
                }
            }
            return true;
        });
    }

    private void findHeaderView() {
        llPicWrapper = header.findViewById(R.id.ll_pic_wrapper);
        llTextWrapper = header.findViewById(R.id.ll_text_wrapper);
        ivNewerPic = header.findViewById(R.id.iv_newer_pic);
        tvNewerPic = header.findViewById(R.id.tv_newer_pic);
        ivSmartPic = header.findViewById(R.id.iv_smart_pic);
        tvSmartPic = header.findViewById(R.id.tv_smart_pic);
        ivSharePic = header.findViewById(R.id.iv_share_pic);
        tvSharePic = header.findViewById(R.id.tv_share_pic);
        tvHasNotBackup = header.findViewById(R.id.tv_has_no_backup);
        backupWrapper = header.findViewById(R.id.ll_has_no_backup_wrapper);
    }

    private void scanImages() {
        if (loaderManager.getLoader(0) == null) {
            loaderManager.initLoader(0, null, GalleryFragment.this);
        } else {
            loaderManager.restartLoader(0, null, GalleryFragment.this);
        }
    }

    private void listenScroll() {
        float[] y = {0, 0, 0, 0};
        header.post(new Runnable() {
            @Override
            public void run() {
                y[0] = 0;
                y[1] = (DensityUtil.getScreenWidth() - DensityUtil.dip2px(74)) / 2f;
                y[2] = DensityUtil.dip2px(93);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                y[0] += dy;
                float percent = 0;
                if (y[0] <= y[2] && y[0] >= 0 && y[2] != 0) {
                    percent = (0 - y[0]) / y[2];
                }
                if (y[0] >= y[2] && y[2] != 0) {
                    percent = -1;
                }
                if (percent != y[3]) {
                    tvTitle.setTranslationX(y[1] * percent);
                    tvTitle.setAlpha(1 + percent);
                    rlTabWrapper.setAlpha(0 - percent);
                    rlTabWrapper.setEnabled(0 - percent >= 0.5f);
                    y[3] = percent;
                }
            }
        });
    }

    @OnClick(R2.id.iv_backup)
    public void onBackupClick() {
        startActivity(GalleryBackupActivity.class);
    }

    @OnClick(R2.id.tv_tips)
    public void onTvTipsClick(){
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
        tvHasNotBackup.setText(String.format("检测到您有%s张未备份的照片，是否备份？", imageBeans.size()));
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
