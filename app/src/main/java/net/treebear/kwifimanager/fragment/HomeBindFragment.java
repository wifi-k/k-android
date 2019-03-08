package net.treebear.kwifimanager.fragment;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.family.FamilyMemberActivity;
import net.treebear.kwifimanager.activity.family.ParentControlActivity;
import net.treebear.kwifimanager.activity.message.MessageListActivity;
import net.treebear.kwifimanager.adapter.ChildrenCarefulAdapter;
import net.treebear.kwifimanager.adapter.MobilePhoneAdapter;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.bean.NoticeBean;
import net.treebear.kwifimanager.test.BeanTest;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.widget.marquee.MarqueeTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link BaseFragment} subclass.
 *
 * @author Administrator
 */
public class HomeBindFragment extends BaseFragment {


    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_back_home)
    TextView tvBackHome;
    @BindView(R.id.tv_ap_name)
    TextView tvApName;
    @BindView(R.id.tv_user_state)
    TextView tvUserState;
    @BindView(R.id.tv_root_name)
    TextView tvRootName;
    @BindView(R.id.tv_invite_member)
    TextView tvInviteMember;
    @BindView(R.id.tv_my_k)
    TextView tvMyK;
    @BindView(R.id.marquee_notice)
    MarqueeTextView marqueeNotice;
    @BindView(R.id.tv_parent_control)
    TextView tvParentControl;
    @BindView(R.id.tv_healthy_model)
    TextView tvHealthyModel;
    @BindView(R.id.tv_look_week_report)
    TextView tvLookWeekReport;
    @BindView(R.id.tv_has_no_backup)
    TextView tvHasNoBackup;
    @BindView(R.id.tv_to_backup)
    TextView tvToBackup;
    @BindView(R.id.ll_has_no_backup_wrapper)
    LinearLayout llHasNoBackupWrapper;
    @BindView(R.id.education_wrapper)
    ConstraintLayout educationWrapper;
    @BindView(R.id.tv_network_speed)
    TextView tvNetworkSpeed;
    @BindView(R.id.rv_device_list)
    RecyclerView rvDeviceList;
    @BindView(R.id.tv_look_more)
    TextView tvLookMore;
    @BindView(R.id.rv_children_device)
    RecyclerView rvChildrenDevice;
    private ArrayList<MobilePhoneBean> mobilePhoneList = new ArrayList<>();
    private MobilePhoneAdapter mobilePhoneAdapter;

    private ArrayList<MobilePhoneBean> childrenPhoneList = new ArrayList<>();
    private ArrayList<NoticeBean> noticeList = new ArrayList<>();

    public HomeBindFragment() {

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_bind;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        setTitle(R.string.app_name);
//        设备列表模拟数据
        mobilePhoneList.clear();
        mobilePhoneList.addAll(BeanTest.getHomeMobileList());
        childrenPhoneList.clear();
        childrenPhoneList.addAll(BeanTest.getChildrenPhoneList(1));
        noticeList.clear();
        noticeList.addAll(BeanTest.getNoticeList());
//      设备列表适配器配置
        rvDeviceList.setLayoutManager(new LinearLayoutManager(mContext));
        mobilePhoneAdapter = new MobilePhoneAdapter(mobilePhoneList);
        rvDeviceList.setAdapter(mobilePhoneAdapter);
//      儿童设备
        rvChildrenDevice.setLayoutManager(new LinearLayoutManager(mContext));
        ChildrenCarefulAdapter childrenCarefulAdapter = new ChildrenCarefulAdapter(childrenPhoneList);
        rvChildrenDevice.setAdapter(childrenCarefulAdapter);
//       公告
        marqueeNotice.initMarqueeTextView(BeanTest.getNoticeFromBean(noticeList), (view, position) -> startActivity(MessageListActivity.class));
//        其他
        tvUserState.setText(R.string.online);
        tvApName.setText("xiaok123-4567");
        tvHasNoBackup.setText("您有10张新的照片未备份，是否现在备份?");
        tvNetworkSpeed.setText(String.format("“当前在线%s台/上行网速1000k/下行网速2.4M”", Check.onlineSum(mobilePhoneList)));
        tvLookMore.setVisibility(mobilePhoneList.size() >= 3 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!hasLoadData && isVisibleToUser) {
            // TODO: 2019/3/4 加载数据 加载成功后将hasLoadData改为true
        }
    }

    @OnClick(R.id.tv_back_home)
    public void onTvBackHomeClicked() {
    }

    @OnClick(R.id.tv_root_name)
    public void onTvRootNameClicked() {
        startActivity(FamilyMemberActivity.class);
    }

    @OnClick(R.id.tv_invite_member)
    public void onTvInviteMemberClicked() {
    }

    @OnClick(R.id.tv_my_k)
    public void onTvMyKClicked() {
    }

    @OnClick(R.id.tv_parent_control)
    public void onTvParentControlClicked() {
        startActivity(ParentControlActivity.class);
    }

    @OnClick(R.id.tv_healthy_model)
    public void onTvHealthyModelClicked() {
    }

    @OnClick(R.id.tv_look_week_report)
    public void onTvLookWeekReportClicked() {
    }

    @OnClick(R.id.tv_to_backup)
    public void onTvToBackupClicked() {
    }

    @OnClick(R.id.tv_look_more)
    public void onTvLookMoreClicked() {
    }
}
