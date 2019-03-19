package net.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.bean.AppBean;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.util.DateFormatUtils;

import java.util.List;

public class ChildrenCarefulAdapter extends BaseQuickAdapter<MobilePhoneBean, BaseViewHolder> {
    public ChildrenCarefulAdapter(@Nullable List<MobilePhoneBean> data) {
        super(R.layout.layout_item_children_mobile, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MobilePhoneBean item) {
        switch (item.getType()) {
            case Config.Types.APPLE:
                helper.setImageResource(R.id.iv_children_phone_icon, R.mipmap.ic_device_apple);
                break;
            case Config.Types.ANDROID:
                helper.setImageResource(R.id.iv_children_phone_icon, R.mipmap.ic_device_android);
                break;
            default:
                helper.setImageResource(R.id.iv_children_phone_icon, R.mipmap.ic_device_pad);
                break;
        }
        helper.setText(R.id.tv_mobile_name, item.getName())
                .setText(R.id.tv_average_use_time, String.format("上周平均每日活跃时长：%s", DateFormatUtils.mill2Time(item.getAverageTime())))
                .setText(R.id.tv_use_time_rank, String.format("超过%s%%同龄人上网时长", item.getRank()))
                .addOnClickListener(R.id.tv_look_week_report);
        LinearLayout llAppWrapper = helper.getView(R.id.ll_active_app_wrapper);
        for (AppBean appBean : item.getActiveApp()) {
            ImageView appIcon = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_app_icon, null);
            // TODO: 2019/3/7 替换默认图标
            appIcon.setImageResource(R.mipmap.ic_launcher);
            llAppWrapper.addView(appIcon);
        }

    }
}
