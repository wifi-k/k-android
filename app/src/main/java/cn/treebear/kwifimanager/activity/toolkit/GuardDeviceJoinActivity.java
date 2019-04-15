package cn.treebear.kwifimanager.activity.toolkit;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.server.contract.NodeMobileContract;
import cn.treebear.kwifimanager.mvp.server.presenter.NodeMobilePresenter;

/**
 * 防蹭网列表
 *
 * @author Administrator
 */
public class GuardDeviceJoinActivity extends BaseActivity<NodeMobileContract.Presenter, MobileListBean> implements NodeMobileContract.View {

    @BindView(R2.id.empty_view_wrapper)
    ConstraintLayout emptyViewWrapper;
    @BindView(R2.id.rv_guard_device_list)
    RecyclerView rvGuardDeviceList;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private ArrayList<MobileListBean.MobileBean> guardDeviceList = new ArrayList<>();
    private GuardJoinDeviceAdapter adapter;
    private int pageNo = 1;

    @Override
    public int layoutId() {
        return R.layout.activity_guard_device_join;
    }

    @Override
    public NodeMobileContract.Presenter getPresenter() {
        return new NodeMobilePresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.toolkit_guard_join_net);
        adapter = new GuardJoinDeviceAdapter(guardDeviceList);
        rvGuardDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvGuardDeviceList.setAdapter(adapter);
        adapter.setOnCheckedChangeListener(new GuardJoinDeviceAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked, MobileListBean.MobileBean item) {
                item.setIsBlock(isChecked ? 1 : 0);
                mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(), item.getMac(), item.getNote(), isChecked ? 1 : 0);
            }
        });
        showLoading();
        refresh();
        refreshLayout.setOnRefreshListener(this::refresh);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getNodeMobileList(MyApplication.getAppContext().getCurrentSelectNode(), pageNo += 1);
            }
        }, rvGuardDeviceList);
    }

    private void refresh() {
        mPresenter.getNodeMobileList(MyApplication.getAppContext().getCurrentSelectNode(), pageNo = 1);
    }

    @OnClick(R2.id.tv_add_join)
    public void onViewClicked() {

    }

    @Override
    public void onLoadData(MobileListBean resultData) {
        hideLoading();
        refreshLayout.setRefreshing(false);
        if (resultData.getPage() != null) {
            if (pageNo == 1) {
                guardDeviceList.clear();
            }
            if (resultData.getPage().size() < Config.Numbers.PAGE_SIZE) {
//                adapter.loadMoreEnd(guardDeviceList.size() == 0);
                adapter.loadMoreEnd(true);
            } else {
                adapter.loadMoreComplete();
            }
            guardDeviceList.addAll(resultData.getPage());
//            emptyViewWrapper.setVisibility(guardDeviceList.size() == 0 ? View.VISIBLE : View.GONE);
            emptyViewWrapper.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setNodeMobileSuccess() {
        ToastUtils.showShort(R.string.set_option_success);
    }

    @Override
    public void setNodeMobileFail() {
        ToastUtils.showShort(R.string.option_failed);
        refresh();
    }
}
