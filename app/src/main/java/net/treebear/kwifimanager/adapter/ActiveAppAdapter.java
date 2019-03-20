package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.AppBean;

import java.util.List;

/**
 * @author Administrator
 */
public class ActiveAppAdapter extends BaseQuickAdapter<AppBean, BaseViewHolder> {
    public ActiveAppAdapter(@Nullable List<AppBean> data) {
        super(R.layout.layout_item_active_app, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppBean item) {
        helper.setText(R.id.tv_app_name, item.getName())
                .setImageResource(R.id.iv_app_icon, item.getIconRes());
    }
}
