package cn.treebear.kwifimanager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.activity.home.FamilyMemberActivity;
import cn.treebear.kwifimanager.activity.home.healthy.HealthyModelActivity;
import cn.treebear.kwifimanager.activity.home.mobile.AllMobileListActivity;
import cn.treebear.kwifimanager.activity.home.mobile.MobileDetailActivity;
import cn.treebear.kwifimanager.activity.home.myk.MyDeviceListActivity;
import cn.treebear.kwifimanager.activity.home.myk.SelectXiaoKActivity;
import cn.treebear.kwifimanager.activity.home.parent.ParentControlActivity;
import cn.treebear.kwifimanager.activity.home.report.ChildrenListActivity;
import cn.treebear.kwifimanager.activity.home.report.WeekReportActivity;
import cn.treebear.kwifimanager.activity.message.MessageListActivity;
import cn.treebear.kwifimanager.activity.toolkit.WifiToolkitActivity;
import cn.treebear.kwifimanager.adapter.ChildrenCarefulAdapter;
import cn.treebear.kwifimanager.adapter.MobilePhoneAdapter;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.bean.FamilyMemberBean;
import cn.treebear.kwifimanager.bean.FamilyMemberCover;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.mvp.server.contract.BindHomeContract;
import cn.treebear.kwifimanager.mvp.server.presenter.BindHomePresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.FileSizeUtil;
import cn.treebear.kwifimanager.util.UMShareUtils;
import cn.treebear.kwifimanager.util.UserInfoUtil;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import cn.treebear.kwifimanager.widget.marquee.MarqueeTextView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link BaseFragment} subclass.
 *
 * @author Administrator
 */
public class HomeBindFragment extends BaseFragment<BindHomeContract.Presenter, NodeInfoDetail> implements BindHomeContract.View {

    @BindView(R2.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R2.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R2.id.tv_back_home)
    TextView tvBackHome;
    @BindView(R2.id.tv_ap_name)
    TextView tvApName;
    @BindView(R2.id.tv_user_state)
    TextView tvUserState;
    @BindView(R2.id.tv_root_name)
    TextView tvRootName;
    @BindView(R2.id.tv_user_role)
    TextView tvUserRole;
    @BindView(R2.id.tv_invite_member)
    TextView tvInviteMember;
    @BindView(R2.id.tv_my_k)
    TextView tvMyK;
    @BindView(R2.id.marquee_notice)
    MarqueeTextView marqueeNotice;
    @BindView(R2.id.tv_parent_control)
    TextView tvParentControl;
    @BindView(R2.id.tv_healthy_model)
    TextView tvHealthyModel;
    @BindView(R2.id.tv_look_week_report)
    TextView tvLookWeekReport;
    @BindView(R2.id.tv_has_no_backup)
    TextView tvHasNoBackup;
    @BindView(R2.id.tv_to_backup)
    TextView tvToBackup;
    @BindView(R2.id.ll_has_no_backup_wrapper)
    LinearLayout llHasNoBackupWrapper;
    @BindView(R2.id.education_wrapper)
    ConstraintLayout educationWrapper;
    @BindView(R2.id.tv_network_speed)
    TextView tvNetworkSpeed;
    @BindView(R2.id.rv_device_list)
    RecyclerView rvDeviceList;
    @BindView(R2.id.tv_look_more)
    TextView tvLookMore;
    @BindView(R2.id.rv_children_device)
    RecyclerView rvChildrenDevice;
    @BindView(R2.id.tv_look_more_kid)
    TextView tvLookMoreKid;
    @BindView(R2.id.cl_online_device_wrapper)
    ConstraintLayout clChildrenWrapper;
    @BindView(R2.id.no_device_wrapper)
    LinearLayout noDeviceWrapper;
    @BindView(R2.id.online_wrapper)
    ConstraintLayout clDeviceWrapper;
    @BindView(R2.id.no_children_wrapper)
    LinearLayout noChildrenWrapper;
    private ArrayList<MobileListBean.MobileBean> mobilePhoneList = new ArrayList<>();
    private MobilePhoneAdapter mobilePhoneAdapter;

    private int currentModifyPosition;
    private TInputDialog modifyNameDialog;
    private NodeInfoDetail.NodeBean nodeBean;
    private ArrayList<MessageInfoBean.PageBean> messageList = new ArrayList<>();
    private List<ChildrenListBean.ChildrenBean> childrenList = new ArrayList<>();
    private ChildrenCarefulAdapter childrenCarefulAdapter;
    private int mOnlineTotal;
    private int mScrollY = 0;

    public HomeBindFragment() {
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_bind;
    }

    @Override
    public BindHomeContract.Presenter getPresenter() {
        return new BindHomePresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setTitle(R.string.app_name);
//      设备列表适配器配置
        setMobileListAdapter();
//      儿童设备
        setChildrenListAdapter();
//       公告 及 其他
        updateOtherData();
        mRootView.findViewById(R.id.constraintLayout).requestFocus();
        listenScroll();
    }

    private void listenScroll() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                mScrollY = scrollY;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getAppContext().isNeedUpdateNodeInfo()) {
            mPresenter.getNodeList();
        }
        if (Check.hasContent(MyApplication.getAppContext().getCurrentSelectNode())) {
            mPresenter.getMobileList(MyApplication.getAppContext().getCurrentSelectNode(), 1);
            mPresenter.getChildrenList(MyApplication.getAppContext().getCurrentSelectNode(), 1);
        }
        mPresenter.getMessageList(1);
    }

    @Override
    public void onLoadData(NodeInfoDetail resultData) {
        if (resultData.getPage() == null) {
            return;
        }
        MyApplication.getAppContext().getUser().setNodeSize(resultData.getTotal());
        if (resultData.getPage().size() > 0) {
            nodeBean = searchSelectNode(resultData.getPage());
            if (nodeBean == null) {
                MyApplication.getAppContext().setCurrentNode(new NodeInfoDetail.NodeBean());
                if (mContext instanceof MainActivity) {
                    ((MainActivity) mContext).updateHomeFragment();
                }
                return;
            }
            MyApplication.getAppContext().setSelectNode(nodeBean.getNodeId());
            tvApName.setText(nodeBean.getName());
            tvUserState.setText(nodeBean.getStatus() == 1 ? R.string.online : R.string.offline);
            mPresenter.getMobileList(MyApplication.getAppContext().getCurrentSelectNode(), 1);
            mPresenter.getChildrenList(MyApplication.getAppContext().getCurrentSelectNode(), 1);
            if (MyApplication.getAppContext().getRole() == -1) {
                mPresenter.getFamilyMembers(MyApplication.getAppContext().getCurrentSelectNode());
            }
        } else {
            if (mContext instanceof MainActivity) {
                ((MainActivity) mContext).updateHomeFragment();
            }
        }
    }

    private void updateOtherData() {
        ServerUserInfo userInfo = MyApplication.getAppContext().getUser();
        tvUserRole.setText(userInfo.getRole() == 0 ? getString(R.string.admin) : getString(R.string.member));
        tvRootName.setText(Check.hasContent(userInfo.getName()) ? userInfo.getName() : "用户" + userInfo.getMobile().substring(userInfo.getMobile().length() - 4));
        tvUserState.setText(R.string.online);
        tvApName.setText(R.string.xiaok_xxxx);
    }

    private List<String> convertMessage(ArrayList<MessageInfoBean.PageBean> messageList) {
        ArrayList<String> result = new ArrayList<>();
        for (MessageInfoBean.PageBean bean : messageList) {
            result.add(bean.getContent());
        }
        return result;
    }

    private void setChildrenListAdapter() {
        rvChildrenDevice.setLayoutManager(new LinearLayoutManager(mContext));
        childrenCarefulAdapter = new ChildrenCarefulAdapter(childrenList);
        rvChildrenDevice.setAdapter(childrenCarefulAdapter);
        childrenCarefulAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_look_week_report) {
                startActivity(WeekReportActivity.class);
            }
        });
        tvLookMoreKid.setVisibility(childrenList.size() >= 3 ? View.VISIBLE : View.GONE);
    }

    private void setMobileListAdapter() {
        rvDeviceList.setLayoutManager(new LinearLayoutManager(mContext));
//        rvDeviceList.setLayoutManager(new MyLinearLayoutManager(mContext));
        mobilePhoneAdapter = new MobilePhoneAdapter(mobilePhoneList, 5);
        rvDeviceList.setAdapter(mobilePhoneAdapter);
        mobilePhoneAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Keys.MOBILE, mobilePhoneList.get(position));
            startActivity(MobileDetailActivity.class, bundle);
        });
        mobilePhoneAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            currentModifyPosition = position;
            showModifyNameDialog();
        });
    }


    private void showModifyNameDialog() {
        if (modifyNameDialog == null) {
            modifyNameDialog = new TInputDialog(mContext);
            modifyNameDialog.setTitle(R.string.remark_name);
            modifyNameDialog.setEditHint(R.string.input_name_please);
            modifyNameDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    dismiss(modifyNameDialog);
                }

                @Override
                public void onRightClick(String s) {
                    if (Check.hasContent(s)) {
                        MobileListBean.MobileBean bean = mobilePhoneList.get(currentModifyPosition);
                        mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode()
                                , bean.getMac(), s, bean.getIsBlock());
                        bean.setName(s);
                        bean.setNote(s);
                    } else {
                        ToastUtils.showShort(R.string.device_name_cannot_empty);
                    }
                }
            });
        }
        modifyNameDialog.clearInputText();
        modifyNameDialog.show();
    }

    @OnClick(R2.id.tv_ap_name)
    public void onTvApNameClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.POSITION, 0);
        startActivityForResult(SelectXiaoKActivity.class, bundle, Values.REQUEST_SELECT_NODE);
    }

    @OnClick(R2.id.tv_root_name)
    public void onTvRootNameClicked() {
        if (nodeBean != null && MyApplication.getAppContext().getRole() != -1) {
            Bundle bundle = new Bundle();
            bundle.putString(Keys.NODE_ID, nodeBean.getNodeId());
            startActivity(FamilyMemberActivity.class, bundle);
        } else {
            ToastUtils.showShort(R.string.device_info_has_no_complete_retry);
        }
    }

    @OnClick(R2.id.tv_invite_member)
    public void onTvInviteMemberClicked() {
        UMShareUtils.shareWxLink(getActivity(), "邀请家庭成员", String.format("快来加入我的小K家庭吧，家庭码：%s", nodeBean.getInviteCode()),
                "https://www.treebear.cn", R.mipmap.ic_launcher, null);
    }

    @OnClick(R2.id.tv_my_k)
    public void onTvMyKClicked() {
        startActivity(MyDeviceListActivity.class);
    }

    @OnClick(R2.id.tv_parent_control)
    public void onTvParentControlClicked() {
        startActivity(ParentControlActivity.class);
    }

    @OnClick(R2.id.tv_healthy_model)
    public void onTvHealthyModelClicked() {
        if (Check.hasContent(MyApplication.getAppContext().getCurrentSelectNode())) {
            startActivity(HealthyModelActivity.class);
        }
    }

    @OnClick(R2.id.tv_look_week_report)
    public void onTvLookWeekReportClicked() {
        if (childrenList.size() == 0) {
            ToastUtils.showShort(R.string.has_no_week_add_children);
        } else if (childrenList.size() == 1) {
            Bundle bundle = new Bundle();
            bundle.putString(Keys.MAC, childrenList.get(0).getMac());
            startActivity(WeekReportActivity.class, bundle);
        } else {
            startActivity(ChildrenListActivity.class);
        }
//        startActivity(WeekReportActivity.class);
    }

    @OnClick(R2.id.tv_wifi_settings)
    public void onTvWifiSettingsClicked() {
        startActivity(WifiToolkitActivity.class);
    }

    @OnClick(R2.id.tv_to_backup)
    public void onTvToBackupClicked() {
    }

    @OnClick(R2.id.tv_look_more)
    public void onTvLookMoreClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.TOTAL, mOnlineTotal);
        startActivity(AllMobileListActivity.class, bundle);
    }


    @Override
    public void onDestroy() {
        dismiss(modifyNameDialog);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Values.REQUEST_SELECT_NODE) {
                int position = data.getIntExtra(Keys.POSITION, 0);
                String name = data.getStringExtra(Keys.NAME);
                String nodeId = data.getStringExtra(Keys.NODE_ID);
                tvApName.setText(name);
                MyApplication.getAppContext().setSelectNode(nodeId);
            }
        }
    }

    @Override
    public void onLoadFamilyMembers(BaseResponse<FamilyMemberCover> response) {
        if (response != null && response.getCode() == ApiCode.SUCC) {
            FamilyMemberCover data = response.getData();
            MyApplication.getAppContext().setRole(getRole(data.getPage()));
            tvUserRole.setText(MyApplication.getAppContext().getRole() == 0 ? R.string.admin : R.string.user);
        } else {
            if (response != null) {
                super.onLoadFail(response, response.getMsg(), response.getCode());
            }
        }
    }

    private int getRole(List<FamilyMemberBean> page) {
        for (FamilyMemberBean bean : page) {
            if (bean.getUserId() == UserInfoUtil.getUserInfo().getId()) {
                return bean.getRole();
            }
        }
        return 1;
    }

    @Override
    public void onMessageListResponse(MessageInfoBean data) {
        messageList.clear();
        messageList.addAll(data.getPage());
        if (messageList.size() > 0) {
            marqueeNotice.initMarqueeTextView(convertMessage(messageList), (view, position) -> startActivity(MessageListActivity.class));
        } else {
            marqueeNotice.initMarqueeTextView(ConstConfig.EMPTY_NOTICE, (view, position) -> startActivity(MessageListActivity.class));
        }
    }

    @Override
    public void onMessageListError(BaseResponse error) {
        marqueeNotice.initMarqueeTextView(ConstConfig.EMPTY_NOTICE, (view, position) -> {
        });
    }

    @Override
    public void onMobileListResponse(MobileListBean data) {
        mobilePhoneList.clear();
        mOnlineTotal = data.getTotal();
        rvDeviceList.clearFocus();
        if (nodeBean != null) {
            tvNetworkSpeed.setText(String.format("“当前在线%s台/上行网速%s/S/下行网速%s/S”", data.getTotal(),
                    FileSizeUtil.formatFileSize(nodeBean.getUpstream()),
                    FileSizeUtil.formatFileSize(nodeBean.getDownstream())));
        } else {
            tvNetworkSpeed.setText(String.format("“当前在线%s台/上行网速%skb/s/下行网速%skb/s”", data.getTotal(), 0, 0));
        }
        if (data.getPage().size() > 3) {
            mobilePhoneList.addAll(data.getPage().subList(0, 3));
        } else {
            mobilePhoneList.addAll(data.getPage());
        }
        mobilePhoneAdapter.notifyDataSetChanged();
        tvLookMore.setVisibility(mobilePhoneList.size() >= 3 ? View.VISIBLE : View.GONE);
        noDeviceWrapper.setVisibility(mobilePhoneList.size() == 0 ? View.VISIBLE : View.GONE);
        clDeviceWrapper.setVisibility(mobilePhoneList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onModifyMobileInfoResponse(BaseResponse response) {
        if (response != null && response.getCode() == 0) {
            dismiss(modifyNameDialog);
            mobilePhoneAdapter.notifyDataSetChanged();
            ToastUtils.showShort(R.string.modify_success);
        } else {
            ToastUtils.showShort(R.string.modify_failed);
        }
    }

    @Override
    public void onMobileListError(BaseResponse error) {
        ToastUtils.showShort(R.string.online_device_get_failed);
    }

    @Override
    public void onChildrenListResponse(ChildrenListBean data) {
        childrenList.clear();
        childrenList.addAll(data.getPage());
        childrenCarefulAdapter.notifyDataSetChanged();
        noChildrenWrapper.setVisibility(childrenList.size() == 0 ? View.VISIBLE : View.GONE);
        clChildrenWrapper.setVisibility(childrenList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onChildrenListError(BaseResponse error) {
        noChildrenWrapper.setVisibility(View.VISIBLE);
        clChildrenWrapper.setVisibility(View.GONE);
    }

    private NodeInfoDetail.NodeBean searchSelectNode(List<NodeInfoDetail.NodeBean> page) {
        if (!Check.hasContent(page)) {
            return null;
        }
        for (NodeInfoDetail.NodeBean bean : page) {
            if (bean.getIsSelect() == 1) {
                MyApplication.getAppContext().setCurrentNode(bean);
                return bean;
            }
        }
        MyApplication.getAppContext().setCurrentNode(page.get(0));
        return page.get(0);
    }
}
