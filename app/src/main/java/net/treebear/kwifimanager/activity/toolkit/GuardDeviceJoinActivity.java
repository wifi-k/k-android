package net.treebear.kwifimanager.activity.toolkit;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.test.BeanTest;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 防蹭网列表
 *
 * @author Administrator
 */
public class GuardDeviceJoinActivity extends BaseActivity {

    @BindView(R.id.empty_view_wrapper)
    ConstraintLayout emptyViewWrapper;
    @BindView(R.id.rv_guard_device_list)
    RecyclerView rvGuardDeviceList;
    private ArrayList<MobilePhoneBean> guardDeviceList = new ArrayList<>();
    private GuardJoinDeviceAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.activity_guard_device_join;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.toolkit_guard_join_net);
        guardDeviceList.addAll(BeanTest.getGuardDeviceList());
        adapter = new GuardJoinDeviceAdapter(guardDeviceList);
        rvGuardDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvGuardDeviceList.setAdapter(adapter);
        emptyViewWrapper.setVisibility(guardDeviceList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.tv_add_join)
    public void onViewClicked() {

    }
}
