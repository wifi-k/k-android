package cn.treebear.kwifimanager.activity.message;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.MessageAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MessageInfoBean;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.server.contract.MessageConstract;
import cn.treebear.kwifimanager.mvp.server.presenter.MessagePresenter;

/**
 * @author Administrator
 */
public class MessageListActivity extends BaseActivity<MessageConstract.Presenter, MessageInfoBean> implements MessageConstract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView rvMessageList;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.tv_empty_view)
    TextView tvEmptyView;
    int pageNo = 1;
    private List<MessageInfoBean.PageBean> messageList = new ArrayList<>();
    private MessageAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public MessageConstract.Presenter getPresenter() {
        return new MessagePresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.message_center);
        showLoading();
        mPresenter.getMessageInfoList(1);
        adapter = new MessageAdapter(messageList);
        rvMessageList.setLayoutManager(new LinearLayoutManager(this));
        rvMessageList.setAdapter(adapter);
        View header = LayoutInflater.from(this).inflate(R.layout.message_header, null, false);
        adapter.addHeaderView(header);
        swipeRefreshLayout.setOnRefreshListener(this::refresh);
        adapter.setOnLoadMoreListener(() -> mPresenter.getMessageInfoList(pageNo += 1), rvMessageList);
    }

    private void refresh() {
        mPresenter.getMessageInfoList(pageNo = 1);
    }

    @Override
    public void onLoadData(MessageInfoBean resultData) {
        hideLoading();
        swipeRefreshLayout.setRefreshing(false);
        adapter.loadMoreComplete();
        if (resultData == null) {
            return;
        }
        if (pageNo == 1) {
            messageList.clear();
        }
        messageList.addAll(resultData.getPage());
        adapter.notifyDataSetChanged();
        if (resultData.getTotal() < Config.Numbers.PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        }
        adapter.setEnableLoadMore(resultData.getTotal() >= Config.Numbers.PAGE_SIZE);
        tvEmptyView.setVisibility(messageList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        swipeRefreshLayout.setRefreshing(false);
        super.onLoadFail(resultData, resultMsg, resultCode);
    }
}
