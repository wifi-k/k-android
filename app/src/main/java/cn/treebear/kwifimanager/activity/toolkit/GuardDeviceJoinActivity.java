package cn.treebear.kwifimanager.activity.toolkit;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.mvp.server.contract.NodeMobileContract;
import cn.treebear.kwifimanager.mvp.server.presenter.NodeMobilePresenter;

/**
 * 防蹭网列表
 *
 * @author Administrator
 */
public class GuardDeviceJoinActivity extends BaseActivity<NodeMobileContract.Presenter, MobileListBean> implements NodeMobileContract.View {

    @BindView(R.id.empty_view_wrapper)
    ConstraintLayout emptyViewWrapper;
    @BindView(R.id.rv_guard_device_list)
    RecyclerView rvGuardDeviceList;
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
        showLoading();
        mPresenter.getNodeMobileList(MyApplication.getAppContext().getCurrentSelectNode(), pageNo);
        adapter = new GuardJoinDeviceAdapter(guardDeviceList);
        rvGuardDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvGuardDeviceList.setAdapter(adapter);
        adapter.setOnCheckedChangeListener(new GuardJoinDeviceAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked, MobileListBean.MobileBean item) {
                item.setIsBlock(isChecked ? 1 : 0);
                mPresenter.setNodeMobileInfo(MyApplication.getAppContext().getCurrentSelectNode(), item.getMac(), item.getNote(), item.getIsBlock());
            }
        });
    }

    @OnClick(R.id.tv_add_join)
    public void onViewClicked() {

    }

    @Override
    public void onLoadData(MobileListBean resultData) {
        hideLoading();
        if (resultData.getPage() != null) {
            if (pageNo == 1) {
                guardDeviceList.clear();
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
    }
}
