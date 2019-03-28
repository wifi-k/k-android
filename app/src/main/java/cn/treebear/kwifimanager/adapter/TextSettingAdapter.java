package cn.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.ItemBean;

public class TextSettingAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {
    public TextSettingAdapter(@Nullable List<ItemBean> data) {
        super(R.layout.layout_item_setting, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemBean item) {
        helper.setText(R.id.tv_item_name, item.getName())
                .setImageResource(R.id.iv_checked, item.isChecked() ? R.mipmap.ic_cb_ok : R.drawable.transparent);
    }
}
