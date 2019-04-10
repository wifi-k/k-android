package cn.treebear.kwifimanager.activity.message;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.MessageAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
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
    int pageNo = 1;
    private List<MessageInfoBean.PageBean> messageList;
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
