package net.treebear.kwifimanager.activity.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.FamilyMemberAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.bean.FamilyMemberBean;
import net.treebear.kwifimanager.bean.FamilyMemberCover;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.FamilyMemberContract;
import net.treebear.kwifimanager.mvp.server.presenter.FamilyMemberPresenter;
import net.treebear.kwifimanager.util.TLog;
import net.treebear.kwifimanager.widget.dialog.TInputDialog;
import net.treebear.kwifimanager.widget.dialog.TMessageDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class FamilyMemberActivity extends BaseActivity<FamilyMemberContract.Presenter, FamilyMemberCover> implements FamilyMemberContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView rvFamilyList;
    private ArrayList<FamilyMemberBean> familyMemberList = new ArrayList<>();
    private FamilyMemberAdapter familyMemberAdapter;
    private TInputDialog tInputDialog;
    private int currentModifyPosition;
    private TMessageDialog tMessageDialog;
    private String nodeId;
    private String tempName = "";

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
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
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.family_member);
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
