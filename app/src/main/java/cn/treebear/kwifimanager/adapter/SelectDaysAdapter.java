package cn.treebear.kwifimanager.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.Daybean;

/**
 * @author Administrator
 */
public class SelectDaysAdapter extends BaseQuickAdapter<Daybean, BaseViewHolder> {
    public SelectDaysAdapter(@Nullable List<Daybean> data) {
        super(R.layout.layout_item_select_days, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Daybean item) {
        helper.setText(R.id.cb_days, item.getName())
                .setChecked(R.id.cb_days, item.isChecked())
                .setOnCheckedChangeListener(R.id.cb_days, (buttonView, isChecked) -> item.setChecked(isChecked));
    }
}
