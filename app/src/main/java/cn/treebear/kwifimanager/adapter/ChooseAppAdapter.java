package cn.treebear.kwifimanager.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.AppBean;

public class ChooseAppAdapter extends BaseQuickAdapter<AppBean, BaseViewHolder> {
    public ChooseAppAdapter(@Nullable List<AppBean> data) {
        super(R.layout.layout_item_choose_app, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppBean item) {
        helper.setText(R.id.tv_app_name, item.getName())
                .setImageResource(R.id.iv_app_icon, item.getIconRes())
                .setChecked(R.id.cb_choose_app, item.isBan());
    }
}
