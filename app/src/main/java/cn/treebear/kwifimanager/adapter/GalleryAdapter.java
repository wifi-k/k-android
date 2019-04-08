package cn.treebear.kwifimanager.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.local.LocalImageBean;
import cn.treebear.kwifimanager.bean.local.LocalImageSection;
import cn.treebear.kwifimanager.config.GalleryHelper;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.DensityUtil;

public class GalleryAdapter extends BaseSectionQuickAdapter<LocalImageSection, BaseViewHolder> {
    private int model = GalleryHelper.IMAGE_MODEL_DISPLAY;
    private ArrayList<LocalImageBean> selectedBeans = new ArrayList<>();

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GalleryAdapter(List<LocalImageSection> data) {
        super(R.layout.item_gallery_image, R.layout.item_gallery_date, data);
    }

    public GalleryAdapter(List<LocalImageSection> data, @GalleryHelper.AdapterModel int adapterModel) {
        super(R.layout.item_gallery_image, R.layout.item_gallery_date, data);
        model = adapterModel;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, LocalImageSection item) {
        helper.setText(R.id.gallery_text, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalImageSection item) {
        ImageView image = helper.getView(R.id.gallery_image);
        CheckBox cbCheck = helper.getView(R.id.cb_image_checked);
        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.height = (DensityUtil.getScreenWidth() - 4) / 3;
        image.setLayoutParams(layoutParams);
        GlideApp.with(helper.itemView)
                .load(item.t.getThumbPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        cbCheck.setVisibility(model == GalleryHelper.IMAGE_MODEL_SELECT ? View.VISIBLE : View.GONE);
        cbCheck.setEnabled(model == GalleryHelper.IMAGE_MODEL_SELECT);
        cbCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (model != GalleryHelper.IMAGE_MODEL_SELECT) {
                return;
            }
            item.t.setSelect(isChecked);
            if (isChecked) {
                selectedBeans.add(item.t);
            } else {
                selectedBeans.remove(item.t);
            }
        });
    }

    public ArrayList<LocalImageBean> getCheckResult() {
        return selectedBeans;
    }

    public void clearCheckStatus() {
        for (LocalImageBean bean : selectedBeans) {
            bean.setSelect(false);
        }
        selectedBeans.clear();
        notifyDataSetChanged();
    }
}
