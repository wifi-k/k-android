package net.treebear.kwifimanager.activity.family;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.FamilyMemberAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.FamilyMemberBean;
import net.treebear.kwifimanager.test.BeanTest;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class FamilyMemberActivity extends BaseActivity {

    @BindView(R.id.rv_family_list)
    RecyclerView rvFamilyList;
    private ArrayList<FamilyMemberBean> familyMemberList = new ArrayList<>();
    private FamilyMemberAdapter familyMemberAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_family_member;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.family_member);
        familyMemberList.clear();
        familyMemberList.addAll(BeanTest.getFamilyMemberList(4));
        rvFamilyList.setLayoutManager(new LinearLayoutManager(this));
        familyMemberAdapter = new FamilyMemberAdapter(familyMemberList);
        rvFamilyList.setAdapter(familyMemberAdapter);
    }
}
