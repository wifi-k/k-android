package cn.treebear.kwifimanager.activity.home.myk;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.bindap.BindAction1Activity;
import cn.treebear.kwifimanager.adapter.MyDeviceAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.mvp.server.contract.MyNodeContract;
import cn.treebear.kwifimanager.mvp.server.presenter.MyNodePresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * @author Administrator
 */
public class MyDeviceListActivity extends BaseActivity<MyNodeContract.Presenter, NodeInfoDetail> implements MyNodeContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView rvDeviceList;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R2.id.tv_empty_view)
    TextView emptyView;
    private MyDeviceAdapter deviceAdapter;
    private TInputDialog tInputDialog;
    private int currentModifyPosition;
    private TMessageDialog tMessageDialog;
    ArrayList<NodeInfoDetail.NodeBean> nodeList = new ArrayList<>();
    private int pageNo = 1;
    private boolean needRefresh = false;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public MyNodeContract.Presenter getPresenter() {
        return new MyNodePresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.my_k_ap, R.string.append);
        deviceAdapter = new MyDeviceAdapter(nodeList);
        rvDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvDeviceList.setAdapter(deviceAdapter);
        mPresenter.getNodeList(pageNo);
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
        refreshLayout.setOnRefreshListener(this::refresh);
        deviceAdapter.setOnLoadMoreListener(() -> mPresenter.getNodeList(++pageNo), rvDeviceList);
    }

    private void refresh() {
        pageNo = 1;
        mPresenter.getNodeList(pageNo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needRefresh) {
            refresh();
        }
    }

    @Override
    public void onLoadData(NodeInfoDetail resultData) {
        refreshLayout.setRefreshing(false);
        needRefresh = false;
        if (pageNo == 1) {
            nodeList.clear();
        }
        List<NodeInfoDetail.NodeBean> page = resultData.getPage();
        if (Check.hasContent(page)) {
            if (page.size() < Config.Numbers.PAGE_SIZE) {
                deviceAdapter.setEnableLoadMore(false);
                deviceAdapter.loadMoreEnd();
            } else {
                deviceAdapter.loadMoreComplete();
            }
            nodeList.addAll(page);
        }
        if (nodeList.size() == 0) {
            deviceAdapter.setEnableLoadMore(false);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        deviceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onTitleRightClick() {
        Bundle bundle = new Bundle();
        needRefresh = true;
        bundle.putInt(Keys.TYPE, Values.TYPE_APPEND_NODE);
        startActivity(BindAction1Activity.class, bundle);
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
        needRefresh = true;
        nodeList.remove(currentModifyPosition);
        MyApplication.getAppContext().getUser().setNodeSize(nodeList.size());
        deviceAdapter.notifyDataSetChanged();
        ToastUtils.showShort("解绑成功");
    }

    @Override
    public void upgradeNodeVersion(int resultCode, String msg) {

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
                        public void onClickLeft(android.view.View view) {
                            tMessageDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(android.view.View view) {
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
    protected void onDestroy() {
        dismiss(tInputDialog, tMessageDialog);
        super.onDestroy();
    }
}
