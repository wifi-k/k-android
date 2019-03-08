package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.TimeLimitBean;

import java.util.List;

public class HealthyModelAdapter extends BaseQuickAdapter<TimeLimitBean, BaseViewHolder> {

    public HealthyModelAdapter(@Nullable List<TimeLimitBean> data) {
        super(R.layout.layout_item_healthy, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeLimitBean item) {
        helper.setText(R.id.tv_healthy_time, String.format("%s-%s", item.getStartTime(), item.getEndTime()))
                .addOnClickListener(R.id.iv_healthy_edit);
    }
}
