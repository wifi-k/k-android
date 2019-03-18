package net.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
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
        List<MobilePhoneBean> banMobile = params.getParcelableArrayList(Keys.PARENT_CONTROL_DEVICE);
        ArrayList<MobilePhoneBean> mobilePhoneList = BeanTest.getMobilePhoneList(10);
        if (banMobile != null) {
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
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.PARENT_CONTROL_DEVICE, convert(adapter.getData()));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onTitleLeftClick();
    }

    private ArrayList<MobilePhoneBean> convert(List<MobilePhoneBean> data) {
        ArrayList<MobilePhoneBean> result = new ArrayList<>();
        for (MobilePhoneBean datum : data) {
            if (datum.isBanOnline()){
                result.add(datum);
            }
        }
        return result;
    }
}
