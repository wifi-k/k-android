package net.treebear.kwifimanager.activity.message;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.MessageAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.MessageInfoBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.server.contract.MessageConstract;
import net.treebear.kwifimanager.mvp.server.presenter.MessagePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class MessageListActivity extends BaseActivity<MessageConstract.IMessagePresenter, MessageInfoBean> implements MessageConstract.IMessageView {

    @BindView(R.id.recycler_view)
    RecyclerView rvMessageList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    int pageNo = 1;
    private List<MessageInfoBean.PageBean> messageList;
    private MessageAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public MessageConstract.IMessagePresenter getPresenter() {
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
        adapter.setEnableLoadMore(true);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getMessageInfoList(pageNo = 1));
        adapter.setOnLoadMoreListener(() -> mPresenter.getMessageInfoList(pageNo += 1), rvMessageList);
    }

    @Override
    public void onLoadData(MessageInfoBean resultData) {
        hideLoading();
        swipeRefreshLayout.setRefreshing(false);
        if (resultData == null) return;
        if (pageNo == 1) {
            messageList.clear();
        }
        messageList.addAll(resultData.getPage());
        adapter.notifyDataSetChanged();
        if (messageList.size() < Config.Numbers.PAGE_SIZE) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }

}
