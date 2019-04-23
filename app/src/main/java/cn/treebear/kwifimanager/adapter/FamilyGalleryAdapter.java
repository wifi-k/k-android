package cn.treebear.kwifimanager.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.local.LocalFamilyGalleryBean;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.CornerTransform;
import cn.treebear.kwifimanager.util.DensityUtil;

public class FamilyGalleryAdapter extends BaseMultiItemQuickAdapter<LocalFamilyGalleryBean, BaseViewHolder> {
    private CornerTransform tsfAll = new CornerTransform(mContext, DensityUtil.dip2px(8));
    private CornerTransform tsfLeft = new CornerTransform(mContext, DensityUtil.dip2px(8));
    private CornerTransform tsfRightTop = new CornerTransform(mContext, DensityUtil.dip2px(8));
    private CornerTransform tsfRightBtm = new CornerTransform(mContext, DensityUtil.dip2px(8));

    public FamilyGalleryAdapter(@Nullable List<LocalFamilyGalleryBean> data) {
        super(data);
        addItemType(ConstConfig.FAMILY_GALLERY_TYPE_1, R.layout.layout_item_gallery_type1);
        addItemType(ConstConfig.FAMILY_GALLERY_TYPE_3, R.layout.layout_item_gallery_type3);
        addItemType(ConstConfig.FAMILY_GALLERY_TYPE_5, R.layout.layout_item_gallery_type5);
        tsfAll.setExceptCorner(false, false, false, false);
        tsfLeft.setExceptCorner(false, true, false, true);
        tsfRightTop.setExceptCorner(true, false, true, true);
        tsfRightBtm.setExceptCorner(true, true, true, false);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalFamilyGalleryBean item) {
        LinearLayout llImageWrapper = helper.getView(R.id.ll_image_wrapper);
        ViewGroup.LayoutParams params = llImageWrapper.getLayoutParams();
        if (params != null) {
            params.height = params.width / 2;
            llImageWrapper.setLayoutParams(params);
        }
        switch (item.getItemType()) {
            case ConstConfig.FAMILY_GALLERY_TYPE_1:
                oneImage(helper, item);
                break;
            case ConstConfig.FAMILY_GALLERY_TYPE_3:
                threeImage(helper, item);
                break;
            case ConstConfig.FAMILY_GALLERY_TYPE_5:
                fiveImage(helper, item);
                break;
            default:
                break;
        }
    }

    private void fiveImage(BaseViewHolder helper, LocalFamilyGalleryBean item) {
        if (Check.maxThen(item.getImages(), 4)) {
            ImageView iv1 = helper.getView(R.id.iv_image_1);
            ImageView iv2 = helper.getView(R.id.iv_image_2);
            ImageView iv3 = helper.getView(R.id.iv_image_3);
            ImageView iv4 = helper.getView(R.id.iv_image_4);
            ImageView iv5 = helper.getView(R.id.iv_image_5);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(0).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(tsfLeft)
                    .into(iv1);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(1).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(iv2);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(2).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(tsfRightTop)
                    .into(iv3);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(3).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(iv4);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(4).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(tsfRightBtm)
                    .into(iv5);
        } else {
            threeImage(helper, item);
        }
    }

    private void threeImage(BaseViewHolder helper, LocalFamilyGalleryBean item) {
        if (Check.maxThen(item.getImages(), 2)) {
            ImageView iv1 = helper.getView(R.id.iv_image_1);
            ImageView iv2 = helper.getView(R.id.iv_image_2);
            ImageView iv3 = helper.getView(R.id.iv_image_3);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(0).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(tsfLeft)
                    .into(iv1);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(1).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(tsfRightTop)
                    .into(iv2);
            GlideApp.with(helper.itemView)
                    .load(item.getImages().get(2).getFilepath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(tsfRightBtm)
                    .into(iv3);
        } else {
            oneImage(helper, item);
        }
    }

    private void oneImage(BaseViewHolder helper, LocalFamilyGalleryBean item) {
        String filepath;
        ImageView iv1 = helper.getView(R.id.iv_image_1);
        if (Check.hasContent(item.getImages())) {
            filepath = item.getImages().get(0).getFilepath();
            GlideApp.with(helper.itemView)
                    .load(filepath)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_share_album)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(tsfAll)
                    .into(iv1);
        } else {
            GlideApp.with(helper.itemView)
                    .load(R.mipmap.no_share_album)
                    .into(iv1);
        }
    }
}
