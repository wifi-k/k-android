package cn.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.config.GlideApp;

public class FullImageAdapter extends BaseQuickAdapter<LocalImageBean, BaseViewHolder> {
    public FullImageAdapter(@Nullable List<LocalImageBean> data) {
        super(R.layout.layout_item_full_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalImageBean item) {
        ImageView ivFullImage = helper.getView(R.id.iv_full_image);
        GlideApp.with(helper.itemView)
                .load(item.getThumbPath())
                .into(ivFullImage);

    }
}
