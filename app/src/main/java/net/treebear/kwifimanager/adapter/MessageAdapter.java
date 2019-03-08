package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.NoticeBean;
import net.treebear.kwifimanager.util.DateFormatUtils;

import java.util.List;

/**
 * @author Administrator
 */
public class MessageAdapter extends BaseQuickAdapter<NoticeBean, BaseViewHolder> {
    public MessageAdapter(@Nullable List<NoticeBean> data) {
        super(R.layout.layout_item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean item) {
        helper.setText(R.id.tv_message_title,item.getTitle())
                .setText(R.id.tv_message_content,item.getContent())
                .setText(R.id.tv_message_time, DateFormatUtils.formatY_M_dHmm(item.getMills()));
    }
}
