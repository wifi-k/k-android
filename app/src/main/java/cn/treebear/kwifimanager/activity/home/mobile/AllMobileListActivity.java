package cn.treebear.kwifimanager.activity.home.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.MobilePhoneAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import cn.treebear.kwifimanager.mvp.server.presenter.AllMobileListPresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;

/**
 * @author Administrator
 */
public class AllMobileListActivity extends BaseActivity<AllMobileListContract.Presenter, MobileListBean> implements AllMobileListContract.View {

    @BindView(R2.id.tv_online_device_count)
    TextView tvOnlineDeviceCount;
    @BindView(R2.id.tv_upload_speed)
    TextView tvUploadSpeed;
    @BindView(R2.id.tv_download_speed)
    TextView tvDownloadSpeed;
    @BindView(R2.id.rv_device_list)
    RecyclerView rvDeviceList;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private ArrayList<MobileListBean.MobileBean> mobilePhoneList = new ArrayList<>();
    private MobilePhoneAdapter mobilePhoneAdapter;
    private int currentModifyPosition;
    private TInputDialog modifyNameDialog;
    private int pageNo = 1;
    private NodeInfoDetail.NodeBean currentNode;
    private int total;

    @Override
    public int layoutId() {
        return R.layout.activity_all_device_list;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            total = params.getInt(Keys.TOTAL, 0);
        }
    }

    @Override
    public AllMobileListContract.Presenter getPresenter() {
        return new AllMobileListPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.conn_device);
        currentNode = MyApplication.getAppContext().getCurrentNode();
        tvOnlineDeviceCount.setText(String.valueOf(total));
        tvDownloadSpeed.setText(String.valueOf(currentNode.getDownstream()));
        tvUploadSpeed.setText(String.valueOf(currentNode.getUpstream()));
        mobilePhoneAdapter = new MobilePhoneAdapter(mobilePhoneList, 6);
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
        mobilePhoneAdapter.setOnItemClickListener((adapter, view, position) -> {
            currentModifyPosition = position;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Keys.MOBILE, mobilePhoneList.get(position));
            TLog.w(mobilePhoneList.get(position));
            startActivityForResult(MobileDetailActivity.class, bundle, Values.REQUEST_EDIT_DEVICE);
        });
        refreshLayout.setOnRefreshListener(this::refresh);
        mobilePhoneAdapter.setOnLoadMoreListener(() -> mPresenter.getMobileList(currentNode.getNodeId(), ++pageNo, Config.Numbers.PAGE_SIZE), rvDeviceList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        showLoading();
        mPresenter.getMobileList(currentNode.getNodeId(), pageNo = 1, Config.Numbers.PAGE_SIZE);
    }

    private void showModifyNameDialog() {
        if (modifyNameDialog == null) {
            modifyNameDialog = new TInputDialog(this);
            modifyNameDialog.setTitle(R.string.remark_name);
            modifyNameDialog.setEditHint(R.string.input_name_please);
            modifyNameDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    dismiss(modifyNameDialog);
                }

                @Override
                public void onRightClick(String s) {
                    if (Check.hasContent(s)) {
                        MobileListBean.MobileBean bean = mobilePhoneList.get(currentModifyPosition);
                        bean.setName(s);
                        bean.setNote(s);
                        mPresenter.setNodeMobileInfo(currentNode.getNodeId(), bean.getMac(), bean.getNote(), bean.getIsBlock(), bean.getIsRecord(), 1);
                    } else {
                        ToastUtils.showShort(R.string.device_name_cannot_empty);
                    }
                }
            });
        }
        modifyNameDialog.clearInputText();
        modifyNameDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismiss(modifyNameDialog);
        super.onDestroy();
    }

    @Override
    public void onLoadData(MobileListBean resultData) {
        hideLoading();
        mobilePhoneAdapter.loadMoreComplete();
        refreshLayout.setRefreshing(false);
        if (pageNo == 1) {
            mobilePhoneList.clear();
        }
        mobilePhoneList.addAll(resultData.getPage());
        if (resultData.getTotal() <= mobilePhoneList.size()) {
            mobilePhoneAdapter.loadMoreEnd(true);
            tvOnlineDeviceCount.setText(String.valueOf(getOnlineCount(mobilePhoneList)));
        }
        mobilePhoneAdapter.setEnableLoadMore(resultData.getTotal() > mobilePhoneList.size());
        mobilePhoneAdapter.notifyDataSetChanged();
    }

    private int getOnlineCount(ArrayList<MobileListBean.MobileBean> mobilePhoneList) {
        int count = 0;
        for (MobileListBean.MobileBean bean : mobilePhoneList) {
            count += bean.getStatus();
        }
        return count;
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        refreshLayout.setRefreshing(false);
        super.onLoadFail(resultData, resultMsg, resultCode);
        ToastUtils.showShort(R.string.online_device_get_failed);
    }

    @Override
    public void onModifyMobileInfoResponse(BaseResponse response) {
        if (response != null && response.getCode() == 0) {
            dismiss(modifyNameDialog);
            mobilePhoneAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(R.string.modify_failed);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null && resultCode == RESULT_OK) {
//            if (requestCode == Values.REQUEST_EDIT_DEVICE) {
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    MobileListBean.MobileBean mobile = (MobileListBean.MobileBean) extras.getSerializable(Keys.MOBILE);
//                    if (mobile != null) {
//                        mobilePhoneList.set(currentModifyPosition, mobile);
//                        mobilePhoneAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }
    }
}
