package cn.treebear.kwifimanager.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.DensityUtil;

public class GalleryUploadAdapter extends BaseQuickAdapter<LocalImageBean, BaseViewHolder> {
    public GalleryUploadAdapter(@Nullable List<LocalImageBean> data) {
        super(R.layout.item_gallery_image_upload, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalImageBean item) {
        ImageView image = helper.getView(R.id.gallery_image);
        ProgressBar pbUpload = helper.getView(R.id.pb_upload_progress);
        TextView tvUpload = helper.getView(R.id.tv_upload_progress);
        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.height = (DensityUtil.getScreenWidth() - 4) / 3;
        image.setLayoutParams(layoutParams);
        GlideApp.with(helper.itemView)
                .load(item.getThumbPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);

    }
}
