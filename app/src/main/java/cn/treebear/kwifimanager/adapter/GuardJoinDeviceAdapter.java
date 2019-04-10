package cn.treebear.kwifimanager.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.suke.widget.SwitchButton;

import java.util.List;

import androidx.annotation.Nullable;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.DateTimeUtils;

/**
 * @author Administrator
 */
public class GuardJoinDeviceAdapter extends BaseQuickAdapter<MobileListBean.MobileBean, BaseViewHolder> {
    private OnCheckedChangeListener mListener;

    public GuardJoinDeviceAdapter(@Nullable List<MobileListBean.MobileBean> data) {
        super(R.layout.layout_item_ban_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MobileListBean.MobileBean item) {
        boolean isOnline = item.getStatus() == 1;
        helper.setText(R.id.tv_device_name, item.getName())
                .setText(R.id.tv_device_time, isOnline ?
                        DateTimeUtils.createTimeInfoByStatusLength(isOnline, item.getOnTime()) :
                        DateTimeUtils.createTimeInfoByStatusLength(isOnline, item.getOffTime()))
                .setChecked(R.id.sw_guard_device, item.getIsBlock() == 1);
        ImageView ivPhone = helper.getView(R.id.iv_phone_type);
        GlideApp.with(helper.itemView).load(item.getMacIcon())
                .placeholder(R.mipmap.ic_device_pad).error(R.mipmap.ic_device_pad)
                .into(ivPhone);
        if (mListener != null) {
            SwitchButton sb = helper.getView(R.id.sw_guard_device);
            sb.setOnCheckedChangeListener((view, isChecked) -> mListener.onCheckedChanged(sb, isChecked, item));
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mListener = listener;
    }

    public abstract static class OnCheckedChangeListener implements SwitchButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {

        }

        public abstract void onCheckedChanged(SwitchButton view, boolean isCheck, MobileListBean.MobileBean item);
    }

}
