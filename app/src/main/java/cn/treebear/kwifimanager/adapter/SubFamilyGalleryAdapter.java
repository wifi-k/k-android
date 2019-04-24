package cn.treebear.kwifimanager.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.file.FilesBean;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.DensityUtil;

public class SubFamilyGalleryAdapter extends BaseQuickAdapter<FilesBean, BaseViewHolder> {

    private int mTotal = 0;

    public SubFamilyGalleryAdapter(@Nullable List<FilesBean> data, int total) {
        super(R.layout.item_gallery_image, data);
        mTotal = total;
    }

    @Override
    protected void convert(BaseViewHolder helper, FilesBean item) {
        ImageView image = helper.getView(R.id.gallery_image);
        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.height = (DensityUtil.getScreenWidth() - DensityUtil.dip2px(62)) / 3;
        image.setLayoutParams(layoutParams);
        GlideApp.with(helper.itemView)
                .load(item.getThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        helper.setGone(R.id.tv_tail_count, item.isTail());
        if (item.isTail()) {
            helper.setText(R.id.tv_tail_count, String.valueOf(mTotal));
        }
    }
}
