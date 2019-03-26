package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.FamilyMemberBean;
import net.treebear.kwifimanager.config.GlideApp;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FamilyMemberAdapter extends BaseQuickAdapter<FamilyMemberBean, BaseViewHolder> {
    public FamilyMemberAdapter(@Nullable List< FamilyMemberBean> data) {
        super(R.layout.layout_item_family_member, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,  FamilyMemberBean item) {
        CircleImageView header = helper.getView(R.id.iv_member_header);
        GlideApp.with(helper.itemView).load(item.getUserAvatar()).placeholder(R.mipmap.logo).error(R.mipmap.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop().into(header);
        helper.setText(R.id.tv_member_nickname, item.getUserName())
                .setText(R.id.tv_member_mobile, String.format("手机号：%s", item.getUserMobile()))
                .setImageResource(R.id.iv_member_admin, item.getRole() == 0 ?
                        R.mipmap.ic_member_admin_yes : R.mipmap.ic_member_admin_no)
                .addOnClickListener(R.id.iv_member_admin)
                .addOnClickListener(R.id.iv_member_edit)
                .addOnClickListener(R.id.iv_member_delete);
    }
}
