package cn.treebear.kwifimanager.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;

public class ChooseXiaoKAdapter extends BaseQuickAdapter<NodeInfoDetail.NodeBean, BaseViewHolder> {
    public ChooseXiaoKAdapter(@Nullable List<NodeInfoDetail.NodeBean> data) {
        super(R.layout.layout_item_choose_xiaok, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NodeInfoDetail.NodeBean item) {
        helper.setText(R.id.tv_node_name, item.getName())
                .setText(R.id.tv_serial_id, item.getNodeId())
                .setText(R.id.tv_online_status, item.getStatus() == 1 ? "在线" : "离线")
                .setVisible(R.id.iv_select_status, item.getIsSelect() == 1);
    }
}
