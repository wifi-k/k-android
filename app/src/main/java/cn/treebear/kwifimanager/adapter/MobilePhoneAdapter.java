package cn.treebear.kwifimanager.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.DateTimeUtils;

/**
 * @author Administrator
 */
public class MobilePhoneAdapter extends BaseQuickAdapter<MobileListBean.MobileBean, BaseViewHolder> {

    private int mEmsLimit;

    public MobilePhoneAdapter(@Nullable List<MobileListBean.MobileBean> data, int maxEms) {
        super(R.layout.layout_item_mobile_home, data);
        mEmsLimit = maxEms;
    }

    @Override
    protected void convert(BaseViewHolder helper, MobileListBean.MobileBean item) {
        ImageView ivPhone = helper.getView(R.id.iv_phoneType);
        GlideApp.with(helper.itemView)
                .load(item.getMacIcon())
                .placeholder(R.mipmap.ic_device_pad).error(R.mipmap.ic_device_pad)
                .into(ivPhone);
        boolean isOnline = item.getStatus() == 1;
        TextView view = helper.getView(R.id.tv_phone_name);
        view.setMaxEms(mEmsLimit);
        helper.setText(R.id.tv_phone_name, Check.hasContent(item.getNote()) ? item.getNote() : item.getName())
                .setText(R.id.tv_onoff_time, String.format((isOnline ? "上线" : "离线")
                                + "时间:%s",
                        DateTimeUtils.formatMDHmm(isOnline ? item.getOnTime() : item.getOffTime())))
                .setTextColor(R.id.tv_onoff_type, isOnline
                        ? Config.Colors.DEVICE_ONLINE : Config.Colors.DEVICE_OFFLINE)
                .setText(R.id.tv_onoff_type, isOnline ? R.string.online : R.string.offline)
                .addOnClickListener(R.id.tv_remark);
    }
}
