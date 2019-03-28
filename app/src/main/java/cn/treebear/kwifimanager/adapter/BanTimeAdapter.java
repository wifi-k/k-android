package cn.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.BanAppPlanBean;

/**
 * @author Administrator
 */
public class BanTimeAdapter extends BaseQuickAdapter<BanAppPlanBean, BaseViewHolder> {
    int[] icons = new int[]{R.mipmap.ic_ban_time_1, R.mipmap.ic_ban_time_2};
    int count = 0;

    public BanTimeAdapter(@Nullable List<BanAppPlanBean> data) {
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
