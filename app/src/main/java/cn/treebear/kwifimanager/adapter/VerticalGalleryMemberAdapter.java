package cn.treebear.kwifimanager.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.FamilyMemberBean;
import cn.treebear.kwifimanager.config.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;

public class VerticalGalleryMemberAdapter extends BaseQuickAdapter<FamilyMemberBean, BaseViewHolder> {

    public VerticalGalleryMemberAdapter(@Nullable List<FamilyMemberBean> data) {
        super(R.layout.item_vertical_member, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyMemberBean item) {
        CircleImageView civHeader = helper.getView(R.id.civ_header);
        helper.setText(R.id.tv_member_name, item.getUserName());
        GlideApp.with(helper.itemView)
                .load(item.getUserAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(civHeader);
    }
}
