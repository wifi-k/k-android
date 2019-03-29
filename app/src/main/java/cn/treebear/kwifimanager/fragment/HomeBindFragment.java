package cn.treebear.kwifimanager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.home.FamilyMemberActivity;
import cn.treebear.kwifimanager.activity.home.WeekReportActivity;
import cn.treebear.kwifimanager.activity.home.healthy.HealthyModelActivity;
import cn.treebear.kwifimanager.activity.home.mobile.AllMobileListActivity;
import cn.treebear.kwifimanager.activity.home.mobile.MobileDetailActivity;
import cn.treebear.kwifimanager.activity.home.myk.MyDeviceListActivity;
import cn.treebear.kwifimanager.activity.home.myk.SelectXiaoKActivity;
import cn.treebear.kwifimanager.activity.home.parent.ParentControlActivity;
import cn.treebear.kwifimanager.activity.message.MessageListActivity;
import cn.treebear.kwifimanager.activity.toolkit.WifiToolkitActivity;
import cn.treebear.kwifimanager.adapter.ChildrenCarefulAdapter;
import cn.treebear.kwifimanager.adapter.MobilePhoneAdapter;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.MobilePhoneBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.mvp.server.contract.BindHomeContract;
import cn.treebear.kwifimanager.mvp.server.presenter.BindHomePresenter;
import cn.treebear.kwifimanager.test.BeanTest;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.TLog;
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
    @BindView(R.id.tv_user_role)
    TextView tvUserRole;
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
    private ArrayList<MobileListBean.MobileBean> mobilePhoneList = new ArrayList<>();
    private MobilePhoneAdapter mobilePhoneAdapter;
    private ArrayList<MobilePhoneBean> childrenPhoneList = new ArrayList<>();

    private int currentModifyPosition;
    private TInputDialog modifyNameDialog;
    private NodeInfoDetail.NodeBean nodeBean;
    private ArrayList<MessageInfoBean.PageBean> messageList = new ArrayList<>();

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
        testData();
//      儿童设备
        setChildrenListAdapter();
//       公告 及 其他
        updateOtherData();
        TLog.i(MyApplication.getAppContext().getUser());
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
                return;
            }
            MyApplication.getAppContext().setSelectNode(nodeBean.getNodeId());
            tvApName.setText(nodeBean.getName());
            tvUserState.setText(nodeBean.getStatus() == 1 ? R.string.online : R.string.offline);
            mPresenter.getMobileList(MyApplication.getAppContext().getCurrentSelectNode(), 1);
        }
    }

    private void updateOtherData() {
        tvUserRole.setText(MyApplication.getAppContext().getUser().getRole() == 0 ? getString(R.string.admin) : getString(R.string.member));
        tvRootName.setText(MyApplication.getAppContext().getUser().getName());
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
        ChildrenCarefulAdapter childrenCarefulAdapter = new ChildrenCarefulAdapter(childrenPhoneList);
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
    }

    private void setMobileListAdapter() {
        rvDeviceList.setLayoutManager(new LinearLayoutManager(mContext));
        mobilePhoneAdapter = new MobilePhoneAdapter(mobilePhoneList);
        rvDeviceList.setAdapter(mobilePhoneAdapter);
        mobilePhoneAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(Keys.POSITION, position);
            startActivity(MobileDetailActivity.class, bundle);
        });
        mobilePhoneAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            currentModifyPosition = position;
            showModifyNameDialog();
        });
    }

    private void testData() {
        childrenPhoneList.clear();
        childrenPhoneList.addAll(BeanTest.getChildrenPhoneList(1));
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
                            , bean.getMac(), s, bean.getBlock());
                    bean.setName(s);
                }
            });
        }
        modifyNameDialog.clearInputText();
        modifyNameDialog.show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!hasLoadData && isVisibleToUser) {
            // TODO: 2019/3/4 加载数据 加载成功后将hasLoadData改为true
        }
    }

    @OnClick(R.id.tv_ap_name)
    public void onTvApNameClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.POSITION, 0);
        startActivityForResult(SelectXiaoKActivity.class, bundle, Values.REQUEST_SELECT_NODE);
    }

    @OnClick(R.id.tv_root_name)
    public void onTvRootNameClicked() {
        if (nodeBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Keys.NODE_ID, nodeBean.getNodeId());
            startActivity(FamilyMemberActivity.class, bundle);
        }
    }

    @OnClick(R.id.tv_invite_member)
    public void onTvInviteMemberClicked() {
        UMShareUtils.shareWxLink(getActivity(), "邀请家庭成员", "快来加入我的小K家庭吧，家庭码：balabala~", "https://www.baidu.com", R.mipmap.logo, null);
    }

    @OnClick(R.id.tv_my_k)
    public void onTvMyKClicked() {
        startActivity(MyDeviceListActivity.class);
    }

    @OnClick(R.id.tv_parent_control)
    public void onTvParentControlClicked() {
        startActivity(ParentControlActivity.class);
    }

    @OnClick(R.id.tv_healthy_model)
    public void onTvHealthyModelClicked() {
        if (Check.hasContent(MyApplication.getAppContext().getCurrentSelectNode())) {
            startActivity(HealthyModelActivity.class);
        }
    }

    @OnClick(R.id.tv_look_week_report)
    public void onTvLookWeekReportClicked() {
        startActivity(WeekReportActivity.class);
    }

    @OnClick(R.id.tv_wifi_settings)
    public void onTvWifiSettingsClicked() {
        startActivity(WifiToolkitActivity.class);
    }

    @OnClick(R.id.tv_to_backup)
    public void onTvToBackupClicked() {
    }

    @OnClick(R.id.tv_look_more)
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
        mobilePhoneList.addAll(data.getPage());
        mobilePhoneAdapter.notifyDataSetChanged();
        tvNetworkSpeed.setText(String.format("“当前在线%s台/上行网速%s/下行网速%s”", 2, nodeBean.getUpstream(), nodeBean.getDownstream()));
        tvLookMore.setVisibility(mobilePhoneList.size() >= 3 ? View.VISIBLE : View.GONE);
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

    private NodeInfoDetail.NodeBean searchSelectNode(List<NodeInfoDetail.NodeBean> page) {
        for (NodeInfoDetail.NodeBean bean : page) {
            if (bean.getIsSelect() == 1) {
                MyApplication.getAppContext().setCurrentNode(bean);
                return bean;
            }
        }
        return null;
    }
}
