package cn.treebear.kwifimanager.adapter;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrq.divider.Divider;

import java.util.ArrayList;
import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.file.FamilyGalleryDetailBean;
import cn.treebear.kwifimanager.bean.file.FamilyGallerySection;
import cn.treebear.kwifimanager.bean.file.FilesBean;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DensityUtil;

public class FamilyGalleryDetailAdapter extends BaseSectionQuickAdapter<FamilyGallerySection, BaseViewHolder> {

    private final Divider divider;
    private GridLayoutManager manager;
    private ArrayMap<String, SubFamilyGalleryAdapter> subAdapters = new ArrayMap<>();
    private ArrayMap<String, ArrayList<FilesBean>> subList = new ArrayMap<>();
    private OnSubClickListener mListener;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public FamilyGalleryDetailAdapter(int layoutResId, int sectionHeadResId, List<FamilyGallerySection> data) {
        super(R.layout.item_family_gallery_images, R.layout.item_family_gallery_date, data);
        manager = new GridLayoutManager(mContext, 3);
        divider = Divider.builder()
                .color(Color.WHITE)// 设颜色
                .width(DensityUtil.dip2px(4))// 设线宽px，用于画垂直线
                .height(DensityUtil.dip2px(4))// 设线高px，用于画水平线
                .build();
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyGallerySection item) {
        helper.setText(R.id.tv_user_name,item.t.getUser().getName());
        RecyclerView rv = helper.getView(R.id.recycler_view);
        TextView tvDelte = helper.getView(R.id.tv_delete);
        ArrayList<FilesBean> files = item.t.getFiles();
        ViewGroup.LayoutParams params = rv.getLayoutParams();
        int itemHeight = (DensityUtil.getScreenWidth() - DensityUtil.dip2px(62)) / 3;
        int dividerHeight = DensityUtil.dip2px(4);
        if (Check.maxThen(files, 6)) {
            params.height = itemHeight * 3 + dividerHeight * 2;
        } else if (Check.maxThen(files, 3)) {
            params.height = itemHeight * 2 + dividerHeight;
        } else if (Check.hasContent(files)) {
            params.height = itemHeight;
        }
        tvDelte.setOnClickListener(null);
        rv.setLayoutParams(params);
        rv.setAdapter(null);
        rv.removeAllViews();
        rv.setLayoutManager(null);
        rv.addItemDecoration(divider);
        rv.setLayoutManager(manager);
        SubFamilyGalleryAdapter subAdapter = getSubAdapter(item.t);
        rv.setAdapter(subAdapter);
        tvDelte.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onClickDelete(item.t);
            }
        });
        subAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mListener != null) {
                mListener.onClickSubImage(item.t, position);
            }
        });
        subAdapter.notifyDataSetChanged();
    }

    @Override
    protected void convertHead(BaseViewHolder helper, FamilyGallerySection item) {
        if (item.isHeader) {
            helper.setText(R.id.gallery_text, item.header);
        }
    }

    public void setOnSubClickListener(OnSubClickListener listener) {
        mListener = listener;
    }

    private SubFamilyGalleryAdapter getSubAdapter(FamilyGalleryDetailBean t) {
        String shareId = t.getShareId();
        if (subAdapters.containsKey(shareId)) {
            return subAdapters.get(shareId);
        } else {
            ArrayList<FilesBean> files = t.getFiles();
            if (!Check.hasContent(files)) {
                if (subList.containsKey(shareId) && subList.get(shareId) != null) {
                    subList.get(shareId).clear();
                } else {
                    ArrayList<FilesBean> array = new ArrayList<>();
                    subList.put(shareId, array);
                }
            } else {
                ArrayList<FilesBean> temp;
                if (Check.maxThen(files, 8)) {
                    temp = new ArrayList<>(files.subList(0, 9));
                    temp.get(8).setTail(true);
                } else {
                    temp = files;
                }
                if (subList.containsKey(shareId) && subList.get(shareId) != null) {
                    subList.get(shareId).clear();
                    subList.get(shareId).addAll(temp);
                } else {
                    subList.put(shareId, temp);
                }
            }
            if (!subAdapters.containsKey(shareId)) {
                SubFamilyGalleryAdapter adapter = new SubFamilyGalleryAdapter(subList.get(shareId), files.size());
                subAdapters.put(shareId, adapter);
            }
            return subAdapters.get(shareId);
        }
    }

    public interface OnSubClickListener {
        void onClickDelete(FamilyGalleryDetailBean t);

        void onClickSubImage(FamilyGalleryDetailBean t, int position);
    }
}
