package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.FamilyMemberBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FamilyMemberAdapter extends BaseQuickAdapter<FamilyMemberBean, BaseViewHolder> {
    public FamilyMemberAdapter(@Nullable List<FamilyMemberBean> data) {
        super(R.layout.layout_item_family_member, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyMemberBean item) {
        CircleImageView header = helper.getView(R.id.iv_member_header);
        header.setImageResource(R.mipmap.logo);
        helper.setText(R.id.tv_member_nickname, item.getName());
        helper.setText(R.id.tv_member_mobile, String.format("手机号：%s", item.getMobile()));
        helper.setImageResource(R.id.iv_member_admin, item.isAdmin() ? R.mipmap.ic_member_admin_yes : R.mipmap.ic_member_admin_no);
    }
}
