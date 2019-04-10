package cn.treebear.kwifimanager.activity.home.report;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.ChildrenCarefulAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.ChildrenListBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.ChildrenListContract;
import cn.treebear.kwifimanager.mvp.server.presenter.ChildrenListPresenter;

public class ChildrenListActivity extends BaseActivity<ChildrenListContract.Presenter, ChildrenListBean> implements ChildrenListContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R2.id.tv_empty_view)
    TextView tvEmptyView;
    private int pageNo = 1;
    private List<ChildrenListBean.ChildrenBean> childrenBeans = new ArrayList<>();
    private ChildrenCarefulAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public ChildrenListContract.Presenter getPresenter() {
        return new ChildrenListPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.children_list);
        setAdapters();
        refreshLayout.setOnRefreshListener(this::refresh);
        refresh();
    }

    private void refresh() {
        pageNo = 1;
        mPresenter.getChildrenList(MyApplication.getAppContext().getCurrentSelectNode(), pageNo);
    }

    private void setAdapters() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChildrenCarefulAdapter(childrenBeans);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(Keys.MAC, childrenBeans.get(position).getMac());
            startActivity(WeekReportActivity.class, bundle);
        });
        adapter.setOnLoadMoreListener(() -> mPresenter.getChildrenList(MyApplication.getAppContext().getCurrentSelectNode(), ++pageNo), recyclerView);
    }

    @Override
    public void onLoadData(ChildrenListBean resultData) {
        refreshLayout.setRefreshing(false);
        if (pageNo == 1) {
            childrenBeans.clear();
        }
        childrenBeans.addAll(resultData.getPage());
        if (resultData.getPage().size() < Config.Numbers.PAGE_SIZE) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
        adapter.notifyDataSetChanged();
        tvEmptyView.setVisibility(childrenBeans.size() == 0 ? View.VISIBLE : View.GONE);
    }
}
