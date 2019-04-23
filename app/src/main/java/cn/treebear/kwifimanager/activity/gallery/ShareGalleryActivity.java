package cn.treebear.kwifimanager.activity.gallery;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.FamilyGalleryAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.local.LocalFamilyGalleryBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

public class ShareGalleryActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R2.id.tv_empty_view)
    TextView tvEmptyView;
    private ArrayList<LocalFamilyGalleryBean> familyAlbums = new ArrayList<>();
    private FamilyGalleryAdapter adapter;
    private TInputDialog tInputDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_share_gallery;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.mipmap.jt, getString(R.string.share_gallery), "", R.mipmap.ic_title_add, true);
        adapter = new FamilyGalleryAdapter(familyAlbums);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        tvEmptyView.setVisibility(Check.hasContent(familyAlbums) ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onTitleRightClick() {
        if (tInputDialog == null) {
            tInputDialog = new TInputDialog(this);
            tInputDialog.setTitle(R.string.create_share_gallery);
            tInputDialog.setEditHint(R.string.input_gallery_name);
            tInputDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    dismiss(tInputDialog);
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/4/23 网络创建相册
                    dismiss(tInputDialog);
                    Bundle bundle = new Bundle();
                    bundle.putLong(Keys.ID, 1);
                    startActivity(FamilyGalleryDetailActivity.class, bundle);
                }
            });
        }
        tInputDialog.clearInputText();
        tInputDialog.show();
    }
}
