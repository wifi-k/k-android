package cn.treebear.kwifimanager.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.FamilyMemberAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.FamilyMemberBean;
import cn.treebear.kwifimanager.bean.FamilyMemberCover;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.FamilyMemberContract;
import cn.treebear.kwifimanager.mvp.server.presenter.FamilyMemberPresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.util.UMShareUtils;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * @author Administrator
 */
public class FamilyMemberActivity extends BaseActivity<FamilyMemberContract.Presenter, FamilyMemberCover> implements FamilyMemberContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView rvFamilyList;
    @BindView(R2.id.tv_bottom_button)
    Button tvAddFamilyMember;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private ArrayList<FamilyMemberBean> familyMemberList = new ArrayList<>();
    private FamilyMemberAdapter familyMemberAdapter;
    private TInputDialog tInputDialog;
    private int currentModifyPosition;
    private TMessageDialog tMessageDialog;
    private String nodeId;
    private String tempName = "";

    @Override
    public int layoutId() {
        return R.layout.layout_title_recycler_botton;
    }

    @Override
    public FamilyMemberContract.Presenter getPresenter() {
        return new FamilyMemberPresenter();
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            nodeId = params.getString(Keys.NODE_ID, "");
        }
        if (!Check.hasContent(nodeId)) {
            nodeId = MyApplication.getAppContext().getCurrentSelectNode();
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.family_member);
        tvAddFamilyMember.setText(R.string._add_family_member);
        mPresenter.getFamilyMembers(nodeId);
        rvFamilyList.setLayoutManager(new LinearLayoutManager(this));
        familyMemberAdapter = new FamilyMemberAdapter(familyMemberList);
        //开启动画（默认为渐显效果）
        familyMemberAdapter.openLoadAnimation();
        rvFamilyList.setAdapter(familyMemberAdapter);
        familyMemberAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TLog.i("点击了" + position);
            currentModifyPosition = position;
            switch (view.getId()) {
                case R.id.iv_member_admin:
//                     FamilyMemberBean memberBean = familyMemberList.get(position);
//                    memberBean.setRole(0);
//                    familyMemberAdapter.notifyDataSetChanged();
                    break;
                case R.id.iv_member_edit:
                    showInputDialog();
                    break;
                case R.id.iv_member_delete:
                    showMessageDialog();
                    break;
                default:
                    break;
            }
        });
        refreshLayout.setOnRefreshListener(() -> mPresenter.getFamilyMembers(nodeId));
    }

    @OnClick(R2.id.tv_bottom_button)
    public void onAddFamilyMemberClicked() {
        UMShareUtils.shareWxLink(this, "邀请家庭成员", "快来加入我的小K家庭吧，家庭码：balabala~", "https://www.baidu.com", R.mipmap.ic_test_logo, null);
    }


    /**
     * 修改名称弹窗
     */
    private void showInputDialog() {
        if (tInputDialog == null) {
            tInputDialog = new TInputDialog(this);
            tInputDialog.setTitle(R.string.modify_name);
            tInputDialog.setEditHint(R.string.input_new_name_please);
            tInputDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    tInputDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    tempName = s;
                    mPresenter.modifyMember(nodeId, familyMemberList.get(currentModifyPosition).getUserName());
                }
            });
        }
        tInputDialog.clearInputText();
        tInputDialog.show();
    }

    /**
     * 删除成员弹窗
     */
    private void showMessageDialog() {
        if (tMessageDialog == null) {
            tMessageDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.delete_member)
                    .content("")
                    .left(R.string.cancel)
                    .right(R.string.confirm)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            tMessageDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            mPresenter.deleteMember(nodeId, familyMemberList.get(currentModifyPosition).getUserId());
                        }
                    });
        }
        tMessageDialog.show();
    }

    @Override
    public void onLoadData(FamilyMemberCover resultData) {
        refreshLayout.setRefreshing(false);
        familyMemberList.clear();
        familyMemberList.addAll(resultData.getPage());
        familyMemberAdapter.notifyDataSetChanged();
    }

    @Override
    public void modifyMemberResponse(BaseResponse response) {
        familyMemberList.get(currentModifyPosition).setUserName(tempName);
        tInputDialog.dismiss();
        familyMemberAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteMemberResponse(BaseResponse response) {
        ToastUtils.showShort(R.string.delete_success);
        familyMemberList.remove(currentModifyPosition);
        familyMemberAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        super.onLoadFail(resultData, resultMsg, resultCode);
    }

    @Override
    protected void onDestroy() {
        dismiss(tInputDialog, tMessageDialog);
        super.onDestroy();
    }
}
