package net.treebear.kwifimanager.activity.home.myk;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.MyDeviceAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.NodeInfoDetail;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.MyNodeContract;
import net.treebear.kwifimanager.mvp.server.presenter.MyNodePresenter;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.widget.TInputDialog;
import net.treebear.kwifimanager.widget.TMessageDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class MyDeviceListActivity extends BaseActivity<MyNodeContract.IMyNodePresenter, NodeInfoDetail> implements MyNodeContract.IMyNodeView {

    @BindView(R.id.recycler_view)
    RecyclerView rvDeviceList;
    private MyDeviceAdapter deviceAdapter;
    private TInputDialog tInputDialog;
    private int currentModifyPosition;
    private TMessageDialog tMessageDialog;
    ArrayList<NodeInfoDetail.NodeBean> nodeList = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public MyNodeContract.IMyNodePresenter getPresenter() {
        return new MyNodePresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.my_k_ap);
        deviceAdapter = new MyDeviceAdapter(nodeList);
        rvDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvDeviceList.setAdapter(deviceAdapter);
        mPresenter.getNodeList();
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
                    bundle.putSerializable(Keys.DEVICE_INFO, nodeList.get(currentModifyPosition));
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
                    .content(String.format("是否确定解绑%s？", nodeList.get(currentModifyPosition).getName()), "#28354C")
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
                            mPresenter.unbindNode(nodeList.get(currentModifyPosition).getNodeId());
                        }
                    });
        }
        tMessageDialog.show();
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
                    mPresenter.modifyNodeName(nodeList.get(currentModifyPosition).getNodeId(), s);
                    nodeList.get(currentModifyPosition).setName(s);
                }
            });
        }
        if (!tInputDialog.isShowing()) {
            tInputDialog.clearInputText();
            tInputDialog.show();
        }
    }

    @Override
    public void onLoadData(NodeInfoDetail resultData) {
        if (Check.hasContent(resultData.getPage())) {
            nodeList.clear();
            nodeList.addAll(resultData.getPage());
        }
        deviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void modifyNodeNameResponse(int resultCode, String msg) {
        if (resultCode == 0) {
            tInputDialog.dismiss();
            ToastUtils.showShort(R.string.modify_success);
            deviceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void unbindNodeResponse(int resultCode, String msg) {
        nodeList.remove(currentModifyPosition);
        deviceAdapter.notifyDataSetChanged();
        ToastUtils.showShort("解绑成功");
    }

    @Override
    public void upgardeNodeVersion(int resultCode, String msg) {

    }
}
