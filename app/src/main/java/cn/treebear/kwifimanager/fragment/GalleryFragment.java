package cn.treebear.kwifimanager.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.karumi.dividers.DividerBuilder;
import com.karumi.dividers.DividerItemDecoration;
import com.karumi.dividers.Layer;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.GalleryAdapter;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DateTimeUtils;
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
    private ImageView ivSmartPic;
    private ImageView ivSharePic;
    private TextView tvHasNotBackup;
    private TextView tvToBackup;

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
    }

    private void findHeaderView() {
        ivNewerPic = header.findViewById(R.id.iv_newer_pic);
        ivSmartPic = header.findViewById(R.id.iv_smart_pic);
        ivSharePic = header.findViewById(R.id.iv_share_pic);
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                TLog.i("dx = %s, dy = %s", dx, dy);
                // 获取header坐标y , 一直header height
                // 坐标 (height - y) / height = 比例
                // 比例 * 1 = alpha
                // 比例 * （屏幕width / 1.2） = title右边距
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] STORE_IMAGES = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Thumbnails.DATA
        };
        imageBeans.clear();
        sections.clear();
        return new CursorLoader(
                mContext.getApplicationContext(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        imageBeans.clear();
        sections.clear();
        if (cursor.moveToNext()) {
            int thumbPathIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            int timeIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);
            int pathIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do {
                String thumbPath = cursor.getString(thumbPathIndex);
                long date = cursor.getLong(timeIndex);
                if (date < DateTimeUtils.YEAR) {
                    date *= 1000;
                }
                String filepath = cursor.getString(pathIndex);
                imageBeans.add(new LocalImageBean(thumbPath, date, DateTimeUtils.formatYMD4Gallery(date), filepath));
            } while (cursor.moveToNext());
            image2Section();
        }
    }

    private void image2Section() {
        if (!Check.hasContent(imageBeans)) {
            return;
        }
        sections.clear();
        Collections.sort(imageBeans, (o1, o2) -> Long.compare(o2.getDateAdded(), o1.getDateAdded()));
        TLog.i(imageBeans);
        String date = imageBeans.get(0).getDate();
        sections.add(new LocalImageSection(true, date));
        for (LocalImageBean bean : imageBeans) {
            if (!date.equals(bean.getDate())) {
                date = bean.getDate();
                sections.add(new LocalImageSection(true, date));
            }
            sections.add(new LocalImageSection(bean));
        }
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
