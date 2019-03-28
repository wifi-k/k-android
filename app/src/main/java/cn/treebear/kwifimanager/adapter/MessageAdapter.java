package cn.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.util.DateTimeUtils;

/**
 * @author Administrator
 */
public class MessageAdapter extends BaseQuickAdapter<MessageInfoBean.PageBean, BaseViewHolder> {
    public MessageAdapter(@Nullable List<MessageInfoBean.PageBean> data) {
        super(R.layout.layout_item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfoBean.PageBean item) {
        helper.setText(R.id.tv_message_title, item.getTitle())
                .setText(R.id.tv_message_content, item.getContent())
                .setText(R.id.tv_message_time, DateTimeUtils.formatY_M_dHmm(item.getCreateTime()));
    }
}
