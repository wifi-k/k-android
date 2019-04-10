package cn.treebear.kwifimanager.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;

public class MyDeviceAdapter extends BaseQuickAdapter<NodeInfoDetail.NodeBean, BaseViewHolder> {
    public MyDeviceAdapter(@Nullable List<NodeInfoDetail.NodeBean> data) {
        super(R.layout.layout_my_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NodeInfoDetail.NodeBean item) {
        helper.setText(R.id.tv_device_name, item.getName())
                .setText(R.id.tv_device_serial, item.getNodeId())
                .setText(R.id.tv_device_online, item.getStatus() == 1 ? R.string.online : R.string.offline)
                .setTextColor(R.id.tv_device_online, item.getStatus() == 1 ? Color.WHITE : Config.Colors.DEVICE_K_OFFLINE)
                .setBackgroundRes(R.id.tv_device_online, item.getStatus() == 1 ? R.drawable.btn_green_to_cyan_r4 : R.drawable.bg_f7_r4)
                .setVisible(R.id.tv_device_divider, mData.indexOf(item) == mData.size())
                .addOnClickListener(R.id.tv_modify_name)
                .addOnClickListener(R.id.tv_unbind_device)
                .addOnClickListener(R.id.tv_update_version);
    }
}
