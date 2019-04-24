package cn.treebear.kwifimanager.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.bean.local.LocalFamilyGalleryDetailBean;

public class FamilyGalleryDetailAdapter extends BaseQuickAdapter<LocalFamilyGalleryDetailBean, BaseViewHolder> {
    public FamilyGalleryDetailAdapter(@Nullable List<LocalFamilyGalleryDetailBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalFamilyGalleryDetailBean item) {

    }
}
