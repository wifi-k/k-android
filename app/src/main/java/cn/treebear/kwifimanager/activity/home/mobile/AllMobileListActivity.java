package cn.treebear.kwifimanager.activity.home.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import cn.treebear.kwifimanager.mvp.server.presenter.AllMobileListPresenter;
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
    private ArrayList<MobileListBean.MobileBean> mobilePhoneList = new ArrayList<>();
    private MobilePhoneAdapter mobilePhoneAdapter;
    private int currentModifyPosition;
    private TInputDialog modifyNameDialog;
    private int pageNo = 1;
    private NodeInfoDetail.NodeBean currentNode;

    @Override
    public int layoutId() {
        return R.layout.activity_all_device_list;
    }

    @Override
    public AllMobileListContract.Presenter getPresenter() {
        return new AllMobileListPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.conn_device);
        currentNode = MyApplication.getAppContext().getCurrentNode();
        tvOnlineDeviceCount.setText(String.valueOf(currentNode.getDisk()));
        tvDownloadSpeed.setText(String.valueOf(currentNode.getDownstream()));
        tvUploadSpeed.setText(String.valueOf(currentNode.getUpstream()));
        mPresenter.getMobileList(currentNode.getNodeId(), pageNo, Config.Numbers.PAGE_SIZE);
        mobilePhoneAdapter = new MobilePhoneAdapter(mobilePhoneList, 8);
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
        mobilePhoneAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Keys.MOBILE, mobilePhoneList.get(position));
                startActivity(MobileDetailActivity.class, bundle);
            }
        });
        mobilePhoneAdapter.setOnLoadMoreListener(() -> mPresenter.getMobileList(currentNode.getNodeId(), ++pageNo, Config.Numbers.PAGE_SIZE), rvDeviceList);
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
                    MobileListBean.MobileBean bean = mobilePhoneList.get(currentModifyPosition);
                    mPresenter.setNodeMobileInfo(currentNode.getNodeId(), bean.getMac(), bean.getNote(), bean.getIsBlock(), bean.getIsRecord(), 1);
                    bean.setName(s);
                    bean.setNote(s);
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
        mobilePhoneAdapter.loadMoreComplete();
        if (pageNo == 1) {
            mobilePhoneList.clear();
        }
        if (resultData.getPage().size() < Config.Numbers.PAGE_SIZE) {
            mobilePhoneAdapter.loadMoreEnd();
        }
        mobilePhoneList.addAll(resultData.getPage());
        mobilePhoneAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
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
}
