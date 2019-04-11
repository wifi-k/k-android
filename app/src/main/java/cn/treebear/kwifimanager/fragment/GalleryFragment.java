package cn.treebear.kwifimanager.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cn.treebear.kwifimanager.activity.gallery.FullImageActivity;
import cn.treebear.kwifimanager.activity.gallery.GalleryBackupActivity;
import cn.treebear.kwifimanager.adapter.GalleryDisplayAdapter;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.Keys;
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
    RelativeLayout rlTabWrapper;
    @BindView(R2.id.iv_backup)
    ImageView ivBackup;
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
    LinearLayout llPicWrapper;
    LinearLayout llTextWrapper;

    @Override
    public int layoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initView() {
        setAdapter();
        loaderManager = LoaderManager.getInstance(this);
        listenScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        scanImags();
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
    }

    private void scanImags() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        if (loaderManager.getLoader(0) == null) {
                            loaderManager.initLoader(0, null, GalleryFragment.this);
                        } else {
                            loaderManager.restartLoader(0, null, GalleryFragment.this);
                        }
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("您拒绝了存储权限，无法管理相册文件");
                    }
                }).request();
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
                    rlTabWrapper.setAlpha(0 - percent);
                    rlTabWrapper.setEnabled(0 - percent >= 0.5f);
                    y[3] = percent;
                }
            }
        });
    }

    @OnClick(R2.id.iv_backup)
    public void onBackupClick(){
        startActivity(GalleryBackupActivity.class);
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
