package cn.treebear.kwifimanager.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.bean.local.AlbumBean;

public class ShareAlbumAdapter extends BaseQuickAdapter<AlbumBean, BaseViewHolder> {
    public ShareAlbumAdapter(@Nullable List<AlbumBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumBean item) {

    }
}
