package net.treebear.kwifimanager.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.MobilePhoneAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.test.BeanTest;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class AllDeviceListActivity extends BaseActivity {

    @BindView(R.id.tv_online_device_count)
    TextView tvOnlineDeviceCount;
    @BindView(R.id.tv_upload_speed)
    TextView tvUploadSpeed;
    @BindView(R.id.tv_download_speed)
    TextView tvDownloadSpeed;
    @BindView(R.id.rv_device_list)
    RecyclerView rvDeviceList;
    private ArrayList<MobilePhoneBean> mobilePhoneList = new ArrayList<>();
    private MobilePhoneAdapter mobilePhoneAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_all_device_list;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.conn_device);
        tvOnlineDeviceCount.setText("20");
        tvDownloadSpeed.setText("3.4");
        tvUploadSpeed.setText("1000");
        mobilePhoneList.addAll(BeanTest.getMobilePhoneList(6));
        mobilePhoneAdapter = new MobilePhoneAdapter(mobilePhoneList);
        rvDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvDeviceList.setAdapter(mobilePhoneAdapter);
    }
}
