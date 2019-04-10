package cn.treebear.kwifimanager.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.HealthyModelBean;

public class HealthyModelAdapter extends BaseQuickAdapter<HealthyModelBean.WifiBean.TimerBean, BaseViewHolder> {

    public HealthyModelAdapter(@Nullable List<HealthyModelBean.WifiBean.TimerBean> data) {
        super(R.layout.layout_item_healthy, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthyModelBean.WifiBean.TimerBean item) {
        helper.setText(R.id.tv_healthy_time, String.format("%s-%s", item.getStartTime(), item.getEndTime()))
                .addOnClickListener(R.id.iv_healthy_edit)
                .addOnClickListener(R.id.iv_delete);
    }
}
