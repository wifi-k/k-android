package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.suke.widget.SwitchButton;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.util.DateTimeUtils;

import java.util.List;

/**
 * @author Administrator
 */
public class GuardJoinDeviceAdapter extends BaseQuickAdapter<MobilePhoneBean, BaseViewHolder> {
    public GuardJoinDeviceAdapter(@Nullable List<MobilePhoneBean> data) {
        super(R.layout.layout_item_ban_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MobilePhoneBean item) {
        helper.setText(R.id.tv_device_name, item.getName())
                .setText(R.id.tv_device_time, DateTimeUtils.createTimeInfoByStatusLength(item.isOnline(),
                        item.isOnline() ? item.getOnlineTime() : item.getOfflineTime()))
                .setChecked(R.id.sw_guard_device, item.isBanOnline());
        switch (item.getType()) {
            case Config.Types.APPLE:
                helper.setImageResource(R.id.iv_phone_type, R.mipmap.ic_device_apple);
                break;
            case Config.Types.ANDROID:
                helper.setImageResource(R.id.iv_phone_type, R.mipmap.ic_device_android);
                break;
            default:
                helper.setImageResource(R.id.iv_phone_type, R.mipmap.ic_device_pad);
                break;
        }
        SwitchButton sb = helper.getView(R.id.sw_guard_device);
        sb.setOnCheckedChangeListener((view, isChecked) -> item.setBanOnline(isChecked));
    }
}
