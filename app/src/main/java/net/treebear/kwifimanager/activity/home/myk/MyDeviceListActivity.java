package net.treebear.kwifimanager.activity.home.myk;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.MyDeviceAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.DeviceBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.test.BeanTest;
import net.treebear.kwifimanager.widget.TInputDialog;
import net.treebear.kwifimanager.widget.TMessageDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class MyDeviceListActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView rvDeviceList;
    private ArrayList<DeviceBean> deviceList = new ArrayList<>();
    private MyDeviceAdapter deviceAdapter;
    private TInputDialog tInputDialog;
    private int currentModifyPosition;
    private TMessageDialog tMessageDialog;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.my_k_ap);
        deviceList.addAll(BeanTest.getDeviceList());
        deviceAdapter = new MyDeviceAdapter(deviceList);
        rvDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvDeviceList.setAdapter(deviceAdapter);
        deviceAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            currentModifyPosition = position;
            switch (view.getId()) {
                case R.id.tv_modify_name:
                    showModifyInputDialog();
                    break;
                case R.id.tv_unbind_device:
                    showUnbindDeviceDialog();
                    break;
                case R.id.tv_update_version:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Keys.DEVICE_INFO, deviceList.get(currentModifyPosition));
                    startActivity(UpdateDeviceVersionActivity.class, bundle);
                    break;
                default:
                    break;
            }
        });
    }

    private void showUnbindDeviceDialog() {
        if (tMessageDialog == null) {
            tMessageDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.unbind_k)
                    .content(String.format("是否确定解绑%s？", deviceList.get(currentModifyPosition).getName()), "#28354C")
                    .left(R.string.cancel)
                    .right(R.string.confirm)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            tMessageDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            tMessageDialog.dismiss();
                            deviceList.remove(currentModifyPosition);
                            deviceAdapter.notifyDataSetChanged();
                            // TODO: 2019/3/11 删除设备
                            ToastUtils.showShort("删除成功");
                        }
                    });
        }

    }

    private void showModifyInputDialog() {
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
                    tInputDialog.dismiss();
                    deviceList.get(currentModifyPosition).setName(s);
                    deviceAdapter.notifyDataSetChanged();
                }
            });
        }
        if (!tInputDialog.isShowing()) {
            tInputDialog.clearInputText();
            tInputDialog.show();
        }
    }

}
