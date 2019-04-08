package cn.treebear.kwifimanager.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.gallery.FullImageActivity;
import cn.treebear.kwifimanager.adapter.GalleryAdapter;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.util.DensityUtil;
import cn.treebear.kwifimanager.util.TLog;

/**
 * @author Administrator
 */
public class GalleryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_ai_gallery)
    TextView tvAiGallery;
    @BindView(R.id.tv_all_gallery)
    TextView tvAllGallery;
    @BindView(R.id.tv_share_gallery)
    TextView tvShareGallery;
    @BindView(R.id.rl_tab_wrapper)
    RelativeLayout rlTabWrapper;
    private ArrayList<LocalImageBean> imageBeans = new ArrayList<>();
    private ArrayList<LocalImageSection> sections = new ArrayList<>();
    private LoaderManager loaderManager;
    private GalleryAdapter galleryAdapter;
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
        galleryAdapter = new GalleryAdapter(sections);
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
                TLog.w("dy = " + dy + "; y[0] = " + y[0] + "; y[1] = " + y[1] + ";percent = " + percent);
                if (percent != y[3]) {
                    tvTitle.setTranslationX(y[1] * percent);
                    rlTabWrapper.setAlpha(0 - percent);
                    rlTabWrapper.setEnabled(0 - percent >= 0.5f);
                    y[3] = percent;
                }
            }
        });
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
