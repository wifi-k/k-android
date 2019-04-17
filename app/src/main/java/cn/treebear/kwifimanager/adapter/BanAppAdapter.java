package cn.treebear.kwifimanager.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;

public class BanAppAdapter extends BaseQuickAdapter<BanAppPlanBean, BaseViewHolder> {
    int[] icons = new int[]{R.mipmap.ic_ban_app_0, R.mipmap.ic_ban_app_list_1, R.mipmap.ic_ban_app_list_2};
    int count = 0;

    public BanAppAdapter(@Nullable List<BanAppPlanBean> data) {
        super(R.layout.item_ban_app_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BanAppPlanBean item) {
        helper.setImageResource(R.id.iv_ban_plan_icon, icons[count++ % icons.length])
                .setText(R.id.tv_ban_plan_name, item.getName())
                .addOnClickListener(R.id.iv_ban_plan_edit)
                .addOnClickListener(R.id.iv_ban_plan_delete);
    }

}
