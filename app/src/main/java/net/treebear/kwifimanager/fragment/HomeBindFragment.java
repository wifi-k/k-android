package net.treebear.kwifimanager.fragment;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.widget.marquee.MarqueeTextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link BaseFragment} subclass.
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
    Unbinder unbinder;

    public HomeBindFragment() {

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_bind;
    }

    @Override
    protected void initView() {
        tvTitleText.setText(R.string.app_name);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!hasLoadData && isVisibleToUser) {
            // TODO: 2019/3/4 加载数据 加载成功后将hasLoadData改为true
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_back_home)
    public void onTvBackHomeClicked() {
    }

    @OnClick(R.id.tv_root_name)
    public void onTvRootNameClicked() {
    }

    @OnClick(R.id.tv_invite_member)
    public void onTvInviteMemberClicked() {
    }

    @OnClick(R.id.tv_my_k)
    public void onTvMyKClicked() {
    }

    @OnClick(R.id.tv_parent_control)
    public void onTvParentControlClicked() {
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
