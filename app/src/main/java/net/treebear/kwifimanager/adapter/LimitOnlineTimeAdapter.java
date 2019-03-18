package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.Daybean;
import net.treebear.kwifimanager.bean.TimeLimitBean;

import java.util.List;

public class LimitOnlineTimeAdapter extends BaseQuickAdapter<TimeLimitBean, BaseViewHolder> {
    public LimitOnlineTimeAdapter(@Nullable List<TimeLimitBean> data) {
        super(R.layout.layout_item_time_limit, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeLimitBean item) {
        helper.setText(R.id.tv_limit_time_name, item.getName())
                .setText(R.id.tv_time_hour, String.format("%s-%s", item.getStartTime(), item.getEndTime()))
                .setText(R.id.tv_time_days, convertDayBean(item.getDays()))
                .addOnClickListener(R.id.iv_edit_time)
                .addOnClickListener(R.id.iv_delete_time);
    }

    private String convertDayBean(List<Daybean> days) {
        StringBuilder sb = new StringBuilder();
        for (Daybean bean : days) {
            sb.append(bean.getName()).append("、");
        }
        return sb.substring(0, sb.length() - 1);
    }


}
