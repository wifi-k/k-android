package cn.treebear.kwifimanager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.mvp.server.contract.BindHomeContract;
import cn.treebear.kwifimanager.mvp.server.presenter.BindHomePresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.UMShareUtils;
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getAppContext().isNeedUpdateNodeInfo()) {
            mPresenter.getNodeList();
        }
        mPresenter.getMessageList(1);
    }

    @Override
    public void onLoadData(NodeInfoDetail resultData) {
        if (resultData.getPage() != null && resultData.getPage().size() > 0) {
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
        }
    }

    private void updateOtherData() {
        ServerUserInfo userInfo = MyApplication.getAppContext().getUser();
        tvUserRole.setText(userInfo.getRole() == 0 ? getString(R.string.admin) : getString(R.string.member));
        tvRootName.setText(Check.hasContent(userInfo.getName()) ? userInfo.getName() : "用户" + userInfo.getMobile().substring(userInfo.getMobile().length() - 4));
        tvUserState.setText(R.string.online);
        tvApName.setText("xiaok123-4567");
        tvHasNoBackup.setText("您有10张新的照片未备份，是否现在备份?");
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
        childrenCarefulAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_look_week_report:
                        startActivity(WeekReportActivity.class);
                        break;
                    default:
                        break;
                }
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
                    modifyNameDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    MobileListBean.MobileBean bean = mobilePhoneList.get(currentModifyPosition);
                    mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode()
                            , bean.getMac(), s, bean.getIsBlock());
                    bean.setName(s);
                    bean.setNote(s);
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
        if (nodeBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Keys.NODE_ID, nodeBean.getNodeId());
            startActivity(FamilyMemberActivity.class, bundle);
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
        startActivity(AllMobileListActivity.class);
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
            switch (requestCode) {
                case Values.REQUEST_SELECT_NODE:
                    int position = data.getIntExtra(Keys.POSITION, 0);
                    String name = data.getStringExtra(Keys.NAME);
                    String nodeId = data.getStringExtra(Keys.NODE_ID);
                    tvApName.setText(name);
                    MyApplication.getAppContext().setSelectNode(nodeId);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onMessageListResponse(MessageInfoBean data) {
        messageList.clear();
        messageList.addAll(data.getPage());
        if (messageList.size() > 0) {
            marqueeNotice.initMarqueeTextView(convertMessage(messageList), (view, position) -> startActivity(MessageListActivity.class));
        } else {
            marqueeNotice.initMarqueeTextView(ConstConfig.EMPTY_NOTICE, (view, position) -> {
            });
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
        tvNetworkSpeed.setText(String.format("“当前在线%s台/上行网速%s/下行网速%s”", getOnlineNumber(data.getPage()), nodeBean.getUpstream(), nodeBean.getDownstream()));
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

    private int getOnlineNumber(List<MobileListBean.MobileBean> page) {
        int count = 0;
        for (MobileListBean.MobileBean bean : page) {
            count += bean.getStatus() == 1 ? 1 : 0;
        }
        return count;
    }

    @Override
    public void onModifyMobileInfoResponse(BaseResponse response) {
        if (response != null && response.getCode() == 0) {
            modifyNameDialog.dismiss();
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
