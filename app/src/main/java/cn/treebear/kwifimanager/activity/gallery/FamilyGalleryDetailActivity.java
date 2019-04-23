package cn.treebear.kwifimanager.activity.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.FamilyGalleryAdapter;
import cn.treebear.kwifimanager.adapter.VerticalGalleryMemberAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.FamilyMemberBean;
import cn.treebear.kwifimanager.bean.local.LocalFamilyGalleryBean;
import cn.treebear.kwifimanager.config.Keys;

public class FamilyGalleryDetailActivity extends BaseActivity {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_add_picture)
    TextView tvAddPicture;
    @BindView(R2.id.textView83)
    TextView textView83;
    private long galleryId;
    private View header;
    private View tvPictureCount;
    private RecyclerView headerRecyclerView;
    private ArrayList<FamilyMemberBean> selectMember = new ArrayList<>();
    private VerticalGalleryMemberAdapter memberAdapter;
    private LinearLayout llMemberFooter;

    @Override
    public int layoutId() {
        return R.layout.activity_family_gallery_detail;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            galleryId = params.getLong(Keys.ID, -1);
        }
    }

    @Override
    protected void initView() {
        initHeaderView();
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initHeaderView() {
        header = LayoutInflater.from(this).inflate(R.layout.header_family_gallery, null, false);
        tvPictureCount = header.findViewById(R.id.tv_picture_count);
        headerRecyclerView = header.findViewById(R.id.header_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        headerRecyclerView.setLayoutManager(manager);
        memberAdapter = new VerticalGalleryMemberAdapter(selectMember);
        headerRecyclerView.setAdapter(memberAdapter);
        llMemberFooter = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.footer_show_member, null, false);
        LinearLayout llAddMember = (LinearLayout) llMemberFooter.findViewById(R.id.ll_add_member);
        LinearLayout llRemoveMember = (LinearLayout) llMemberFooter.findViewById(R.id.ll_remove_member);
        llAddMember.setOnClickListener(v -> {
            // TODO: 2019/4/23 tianjia
        });
        llRemoveMember.setOnClickListener(v -> {
            // TODO: 2019/4/23 yichu
        });
        memberAdapter.addFooterView(llMemberFooter);
    }

}
