package cn.treebear.kwifimanager.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.FamilyMemberBean;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.Check;
import de.hdodenhof.circleimageview.CircleImageView;

public class FamilyMemberAdapter extends BaseQuickAdapter<FamilyMemberBean, BaseViewHolder> {
    private boolean isAdmin = false;

    public FamilyMemberAdapter(@Nullable List<FamilyMemberBean> data, int role) {
        super(R.layout.layout_item_family_member, data);
        isAdmin = role == 0;
    }

    public void setRole(int role) {
        isAdmin = role == 0;
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyMemberBean item) {
        CircleImageView header = helper.getView(R.id.iv_member_header);
        GlideApp.with(helper.itemView).load(item.getUserAvatar()).placeholder(R.mipmap.ic_me_header).error(R.mipmap.ic_me_header)
                .diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop().into(header);
        helper.setText(R.id.tv_member_nickname, item.getUserName())
                .setText(R.id.tv_member_mobile, String.format("手机号：%s", item.getUserMobile()))
                .setImageResource(R.id.iv_member_admin, item.getRole() == 0 ?
                        R.mipmap.ic_member_admin_yes : R.mipmap.ic_member_admin_no)
                .addOnClickListener(R.id.iv_member_delete)
                .setGone(R.id.iv_member_admin, item.getRole() == 0)
                .setGone(R.id.iv_member_delete, isAdmin);

        if (!Check.hasContent(item.getUserName()) && Check.maxThen(item.getUserMobile(), 4)) {
            helper.setText(R.id.tv_member_nickname, "用户" + (item.getUserMobile().substring(item.getUserMobile().length() - 4)));
        }
    }
}
