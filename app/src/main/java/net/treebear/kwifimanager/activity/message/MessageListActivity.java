package net.treebear.kwifimanager.activity.message;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.MessageAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.NoticeBean;
import net.treebear.kwifimanager.test.BeanTest;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class MessageListActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView rvMessageList;
    private ArrayList<NoticeBean> noticeList;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.message_center);
        noticeList = BeanTest.getNoticeList();
        MessageAdapter adapter = new MessageAdapter(noticeList);
        rvMessageList.setLayoutManager(new LinearLayoutManager(this));
        rvMessageList.setAdapter(adapter);
    }
}
