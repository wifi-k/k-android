package net.treebear.kwifimanager.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.DeviceBean;
import net.treebear.kwifimanager.config.Config;

import java.util.List;

public class MyDeviceAdapter extends BaseQuickAdapter<DeviceBean, BaseViewHolder> {
    public MyDeviceAdapter(@Nullable List<DeviceBean> data) {
        super(R.layout.layout_my_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceBean item) {
        helper.setText(R.id.tv_device_name, item.getName())
                .setText(R.id.tv_device_serial, item.getSerialId())
                .setText(R.id.tv_device_online, item.isOnline() ? R.string.online : R.string.offline)
                .setTextColor(R.id.tv_device_online, item.isOnline() ? Color.WHITE : Config.Colors.DEVICE_K_OFFLINE)
                .setBackgroundRes(R.id.tv_device_online, item.isOnline() ? R.drawable.btn_green_to_cyan_r4 : R.drawable.bg_f7_r4)
                .setVisible(R.id.tv_device_divider, mData.indexOf(item) == mData.size())
                .addOnClickListener(R.id.tv_modify_name)
                .addOnClickListener(R.id.tv_unbind_device)
                .addOnClickListener(R.id.tv_update_version);
    }
}
