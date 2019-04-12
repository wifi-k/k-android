package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.suke.widget.SwitchButton;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import cn.treebear.kwifimanager.mvp.server.presenter.AllMobileListPresenter;
import cn.treebear.kwifimanager.util.TLog;

/**
 * @author Administrator
 */
public class ChooseControlMobileActivity extends BaseActivity<AllMobileListContract.Presenter, MobileListBean> implements AllMobileListContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R2.id.tv_empty_view)
    TextView tvEmptyView;
    ArrayList<MobileListBean.MobileBean> mobileList = new ArrayList<>();
    private GuardJoinDeviceAdapter adapter;
    private ArrayList<String> macs = new ArrayList<>();
    private int pageNo = 1;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public AllMobileListContract.Presenter getPresenter() {
        return new AllMobileListPresenter();
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            ArrayList<String> list = params.getStringArrayList(Keys.PARENT_CONTROL_DEVICE);
            if (list != null) {
                macs.addAll(list);
            }
            TLog.w(macs);
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.choose_control_device, R.string.save);
        adapter = new GuardJoinDeviceAdapter(mobileList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        refreshData();
        adapter.setOnCheckedChangeListener(new GuardJoinDeviceAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isCheck, MobileListBean.MobileBean item) {
                if (isCheck) {
                    if (!macs.contains(item.getMac())) {
                        macs.add(item.getMac());
                    }
                } else {
                    macs.remove(item.getMac());
                }
            }
        });
        refreshLayout.setOnRefreshListener(this::refreshData);
        adapter.setOnLoadMoreListener(() -> mPresenter.getMobileList(MyApplication.getAppContext().getCurrentSelectNode(), ++pageNo, Config.Numbers.PAGE_SIZE), recyclerView);
    }

    private void refreshData() {
        mPresenter.getMobileList(MyApplication.getAppContext().getCurrentSelectNode(), pageNo = 1, Config.Numbers.PAGE_SIZE);
    }

    @Override
    protected void onTitleRightClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Keys.PARENT_CONTROL_DEVICE, macs);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        hideLoading();
        finish();
    }

    @Override
    public void onLoadData(MobileListBean resultData) {
        refreshLayout.setRefreshing(false);
        if (pageNo == 1) {
            mobileList.clear();
        }
        if (resultData.getPage().size() < Config.Numbers.PAGE_SIZE) {
            adapter.loadMoreEnd(mobileList.size()==0);
        } else {
            adapter.loadMoreComplete();
        }
        mobileList.addAll(resultData.getPage());
        for (MobileListBean.MobileBean bean : mobileList) {
            bean.setIsBlock(0);
            for (String mac : macs) {
                if (bean.getMac().equals(mac)) {
                    bean.setIsBlock(1);
                }
            }
        }
        adapter.notifyDataSetChanged();
        tvEmptyView.setVisibility(mobileList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        super.onLoadFail(resultData, resultMsg, resultCode);
    }

    @Override
    public void onModifyMobileInfoResponse(BaseResponse response) {

    }
}
