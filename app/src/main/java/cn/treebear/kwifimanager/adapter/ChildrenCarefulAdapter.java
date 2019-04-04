package cn.treebear.kwifimanager.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.util.DateTimeUtils;

public class ChildrenCarefulAdapter extends BaseQuickAdapter<ChildrenListBean.ChildrenBean, BaseViewHolder> {
    public ChildrenCarefulAdapter(@Nullable List<ChildrenListBean.ChildrenBean> data) {
        super(R.layout.layout_item_children_mobile, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChildrenListBean.ChildrenBean item) {
        ImageView view = helper.getView(R.id.iv_children_phone_icon);
        GlideApp.with(helper.itemView).load(item.getMacIcon()).placeholder(R.mipmap.ic_device_pad).error(R.mipmap.ic_device_pad).into(view);
        helper.setText(R.id.tv_mobile_name, item.getMacNote())
                .setText(R.id.tv_average_use_time, String.format("上周平均每日活跃时长：%s", DateTimeUtils.mill2Time(item.getTotalTime())))
//                .setText(R.id.tv_use_time_rank, String.format("超过%s%%同龄人上网时长", item.getRank()))
                .addOnClickListener(R.id.tv_look_week_report);
//        GridLayout llAppWrapper = helper.getView(R.id.ll_active_app_wrapper);
//        llAppWrapper.removeAllViews();
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(DensityUtil.dip2px(mContext, 52), DensityUtil.dip2px(mContext, 36));
//        for (AppBean appBean : item.getActiveApp()) {
//            ImageView appIcon = new ImageView(mContext);
//            appIcon.setLayoutParams(layoutParams);
//            appIcon.setPadding(0, 0, DensityUtil.dip2px(mContext, 16), 0);
////            appIcon.setImageResource(R.mipmap.ic_launcher);
//            GlideApp.with(helper.itemView).load(R.mipmap.ic_launcher).circleCrop().into(appIcon);
//            llAppWrapper.addView(appIcon);
//        }

    }
}
