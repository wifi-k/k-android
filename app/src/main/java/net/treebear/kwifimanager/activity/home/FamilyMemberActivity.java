package net.treebear.kwifimanager.activity.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.FamilyMemberAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.FamilyMemberBean;
import net.treebear.kwifimanager.test.BeanTest;
import net.treebear.kwifimanager.util.TLog;
import net.treebear.kwifimanager.widget.TInputDialog;
import net.treebear.kwifimanager.widget.TMessageDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class FamilyMemberActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView rvFamilyList;
    private ArrayList<FamilyMemberBean> familyMemberList = new ArrayList<>();
    private FamilyMemberAdapter familyMemberAdapter;
    private TInputDialog tInputDialog;
    private int currentModifyPosition;
    private TMessageDialog tMessageDialog;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.family_member);
        familyMemberList.clear();
        familyMemberList.addAll(BeanTest.getFamilyMemberList(4));
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
                    // TODO: 2019/3/8 设置为管理员
                    FamilyMemberBean memberBean = familyMemberList.get(position);
                    memberBean.setAdmin(!memberBean.isAdmin());
                    familyMemberAdapter.notifyDataSetChanged();
                    break;
                case R.id.iv_member_edit:
                    resetInputDialog();
                    tInputDialog.show();
                    break;
                case R.id.iv_member_delete:
                    resetMessageDialog();
                    tMessageDialog.show();
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 修改名称弹窗
     */
    private void resetInputDialog() {
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
                    // TODO: 2019/3/8 编辑成员信息
                    familyMemberList.get(currentModifyPosition).setName(s);
                    tInputDialog.dismiss();
                    familyMemberAdapter.notifyDataSetChanged();
                }
            });
        }
        tInputDialog.clearInputText();
    }

    /**
     * 删除成员弹窗
     */
    private void resetMessageDialog() {
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
                            // TODO: 2019/3/8 删除成员信息
                            familyMemberList.remove(currentModifyPosition);
                            familyMemberAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }

}
