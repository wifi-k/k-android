package cn.treebear.kwifimanager.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;

public class FullImageAdapter extends BaseQuickAdapter<LocalImageBean, BaseViewHolder> {
    public FullImageAdapter(@Nullable List<LocalImageBean> data) {
        super(R.layout.layout_item_full_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalImageBean item) {
        ImageView ivFullImage = helper.getView(R.id.iv_full_image);
        Glide.with(helper.itemView)
                .load(item.getThumbPath())
                .into(ivFullImage);
    }
}
