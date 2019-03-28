package cn.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.MobilePhoneBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.util.DateTimeUtils;

/**
 * @author Administrator
 */
public class MobilePhoneAdapter extends BaseQuickAdapter<MobilePhoneBean, BaseViewHolder> {

    public MobilePhoneAdapter(@Nullable List<MobilePhoneBean> data) {
        super(R.layout.layout_item_mobile_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MobilePhoneBean item) {
        switch (item.getType()) {
            case Config.Types.APPLE:
                helper.setImageResource(R.id.iv_phoneType, R.mipmap.ic_device_apple);
                break;
            case Config.Types.ANDROID:
                helper.setImageResource(R.id.iv_phoneType, R.mipmap.ic_device_android);
                break;
            default:
                helper.setImageResource(R.id.iv_phoneType, R.mipmap.ic_device_pad);
                break;
        }

        helper.setText(R.id.tv_phone_name, item.getName())
                .setText(R.id.tv_onoff_time, String.format((item.isOnline() ? "上线" : "离线")
                                + "时间:%s",
                        DateTimeUtils.formatMDHmm(item.isOnline() ? item.getOnlineTime() : item.getOfflineTime())))
                .setTextColor(R.id.tv_onoff_type, item.isOnline()
                        ? Config.Colors.DEVICE_ONLINE : Config.Colors.DEVICE_OFFLINE)
                .setText(R.id.tv_onoff_type, item.isOnline() ? R.string.online : R.string.offline)
                .addOnClickListener(R.id.tv_remark);
    }
}
