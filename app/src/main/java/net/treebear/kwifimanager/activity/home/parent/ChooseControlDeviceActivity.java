package net.treebear.kwifimanager.activity.home.parent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.BanAppPlanBean;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.test.BeanTest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class ChooseControlDeviceActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ArrayList<MobilePhoneBean> mobileList = new ArrayList<>();
    private GuardJoinDeviceAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public void initParams(Bundle params) {
        BanAppPlanBean needModifyPlan = (BanAppPlanBean) params.getSerializable(Keys.BAN_APP_PLAN);
        ArrayList<MobilePhoneBean> mobilePhoneList = BeanTest.getMobilePhoneList(10);
        if (needModifyPlan != null) {
            List<MobilePhoneBean> banMobile = needModifyPlan.getBanMobile();
            for (MobilePhoneBean bean : banMobile) {
                for (MobilePhoneBean phone : mobilePhoneList) {
                    if (phone.getName().equals(bean.getName())) {
                        phone.setBanOnline(true);
                    }
                }
            }
        }
        mobileList.addAll(mobilePhoneList);
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.choose_control_device, R.string.save);
        adapter = new GuardJoinDeviceAdapter(mobileList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onTitleRightClick() {
        onTitleLeftClick();
    }
}
