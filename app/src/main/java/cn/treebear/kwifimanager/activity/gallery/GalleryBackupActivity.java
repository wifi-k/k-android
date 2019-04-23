package cn.treebear.kwifimanager.activity.gallery;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dividers.DividerBuilder;
import com.karumi.dividers.DividerItemDecoration;
import com.karumi.dividers.Layer;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.GalleryUploadAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.util.DensityUtil;
import cn.treebear.kwifimanager.util.TLog;

public class GalleryBackupActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_backup_status_sticky)
    TextView tvBackupStatusSticky;
    @BindView(R2.id.tv_pause_backup_sticky)
    TextView tvPauseBackupSticky;
    @BindView(R2.id.cl_header_sticky_wrapper)
    ConstraintLayout clHeaderWrapper;
    private TextView tvBackupTip;
    private TextView tvUploadSpeed;
    private TextView tvDeskRoom;
    private ProgressBar pbDeskRoom;
    private TextView tvBackupStatus;
    private TextView tvPauseBackup;
    private ConstraintLayout galleryHeaderSticky;
    private GalleryUploadAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.activity_gallery_backup;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.pic_backup);
        setAdapter();
        tvBackupTip.setText(Html.fromHtml("正在自动备份照片，剩余<font color='#4ED6ED'>53</font>张"));
        tvUploadSpeed.setText(String.format("%sB/s", 80));
    }

    private void setAdapter() {
        ConstraintLayout header = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.gallery_backup_header, null, false);
        tvBackupTip = header.findViewById(R.id.tv_backup_tip);
        tvUploadSpeed = header.findViewById(R.id.tv_upload_speed);
        tvDeskRoom = header.findViewById(R.id.tv_desk_room);
        pbDeskRoom = header.findViewById(R.id.pb_desk_room);
        tvBackupStatus = header.findViewById(R.id.tv_backup_status);
        tvPauseBackup = header.findViewById(R.id.tv_pause_backup);
        galleryHeaderSticky = header.findViewById(R.id.gallery_header_sticky);
        tvPauseBackup.setOnClickListener(v -> onViewClicked());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(new Layer(DividerBuilder.get().with(new ColorDrawable(Color.WHITE)).build())));
        adapter = new GalleryUploadAdapter(GalleryHelper.getImageBeans());
        adapter.addHeaderView(header);
        recyclerView.setAdapter(adapter);
        listenScroll();
    }

    private void listenScroll() {
        final int[] scroll = {0, DensityUtil.dip2px(140)};
        recyclerView.post(() -> scroll[1] = galleryHeaderSticky.getTop());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scroll[0] += dy;
                TLog.w("scroll[0] = " + scroll[0] + ", scroll[1] = " + scroll[1] + ", dy = " + dy);
                clHeaderWrapper.setVisibility(scroll[0] >= scroll[1] ? View.VISIBLE : View.GONE);
            }
        });
    }

    @OnClick(R2.id.tv_pause_backup_sticky)
    public void onViewClicked() {

    }

}
