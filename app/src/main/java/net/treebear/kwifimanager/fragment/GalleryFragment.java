package net.treebear.kwifimanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.test.BeanTest;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class GalleryFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new GuardJoinDeviceAdapter(BeanTest.getMobilePhoneList(10)));
        listenScroll();
    }

    private void listenScroll() {

    }

}
