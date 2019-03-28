package cn.treebear.kwifimanager.activity.home.myk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.ChooseXiaoKAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.NodeInfoDetail;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.SelectXiaoKContract;
import cn.treebear.kwifimanager.mvp.server.presenter.SelectXiaoKPresenter;
import cn.treebear.kwifimanager.util.DensityUtil;
import cn.treebear.kwifimanager.widget.divider.RecyclerViewDividerItemDecoration;

public class SelectXiaoKActivity extends BaseActivity<SelectXiaoKContract.Presenter, NodeInfoDetail> implements SelectXiaoKContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private int pageNo = 1;
    private List<NodeInfoDetail.NodeBean> nodeBeans = new ArrayList<>();
    private ChooseXiaoKAdapter adapter;
    private int mCurrentPosition;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            mCurrentPosition = params.getInt(Keys.POSITION, 0);
        }
    }

    @Override
    public SelectXiaoKContract.Presenter getPresenter() {
        return new SelectXiaoKPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.switch_xiaok);
        mPresenter.getXiaoKList(pageNo);
        adapter = new ChooseXiaoKAdapter(nodeBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerViewDividerItemDecoration(Config.Colors.COLOR_EF,
                1, DensityUtil.dip2px(this, 18), 0));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            mCurrentPosition = position;
            showLoading(R.string.change_node_ing);
            mPresenter.selectXiaoK(nodeBeans.get(position).getNodeId());
        });
        refreshLayout.setOnRefreshListener(() -> {
            pageNo = 1;
            mPresenter.getXiaoKList(pageNo);
        });
    }

    @Override
    public void onLoadData(NodeInfoDetail resultData) {
        refreshLayout.setRefreshing(false);
        adapter.loadMoreComplete();
        if (pageNo == 1) {
            nodeBeans.clear();
        }
        adapter.setEnableLoadMore(resultData.getPage().size() >= Config.Numbers.PAGE_SIZE);
        nodeBeans.addAll(resultData.getPage());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSelectXiaoK(BaseResponse response) {
        hideLoading();
        ToastUtils.showShort(String.format("已切换至%s", nodeBeans.get(mCurrentPosition).getName()));
        Intent intent = new Intent();
        intent.putExtra(Keys.POSITION, mCurrentPosition);
        intent.putExtra(Keys.NAME, nodeBeans.get(mCurrentPosition).getName());
        intent.putExtra(Keys.NODE_ID, nodeBeans.get(mCurrentPosition).getNodeId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
