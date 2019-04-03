package cn.treebear.kwifimanager.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.DensityUtil;

public class GalleryAdapter extends BaseSectionQuickAdapter<LocalImageSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GalleryAdapter(List<LocalImageSection> data) {
        super(R.layout.item_gallery_image, R.layout.item_gallery_date, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, LocalImageSection item) {
        helper.setText(R.id.gallery_text, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalImageSection item) {
        ImageView image = helper.getView(R.id.gallery_image);
        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.height = (DensityUtil.getScreenWidth()-4)/3;
        image.setLayoutParams(layoutParams);
        GlideApp.with(helper.itemView)
                .load(item.t.getFilepath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);

    }
}
