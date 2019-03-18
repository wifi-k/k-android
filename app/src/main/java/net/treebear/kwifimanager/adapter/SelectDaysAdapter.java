package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.Daybean;

import java.util.List;

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
