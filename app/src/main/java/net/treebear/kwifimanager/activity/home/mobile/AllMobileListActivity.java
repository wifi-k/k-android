package net.treebear.kwifimanager.activity.home.mobile;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.MobilePhoneAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.MobilePhoneBean;
import net.treebear.kwifimanager.test.BeanTest;
import net.treebear.kwifimanager.widget.TInputDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class AllMobileListActivity extends BaseActivity {

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
    private int currentModifyPosition;
    private TInputDialog modifyNameDialog;

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
        mobilePhoneAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            currentModifyPosition = position;
            switch (view.getId()) {
                default:
                    showModifyNameDialog();
                    break;
            }
        });
    }

    private void showModifyNameDialog() {
        if (modifyNameDialog == null) {
            modifyNameDialog = new TInputDialog(this);
            modifyNameDialog.setTitle(R.string.remark_name);
            modifyNameDialog.setEditHint(R.string.input_name_please);
            modifyNameDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    modifyNameDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/3/13 修改信息
                    modifyNameDialog.dismiss();
                    mobilePhoneList.get(currentModifyPosition).setName(s);
                    mobilePhoneAdapter.notifyDataSetChanged();
                }
            });
        }
        modifyNameDialog.clearInputText();
        modifyNameDialog.show();
    }
}
