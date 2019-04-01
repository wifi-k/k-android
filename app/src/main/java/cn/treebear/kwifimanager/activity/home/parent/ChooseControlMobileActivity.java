package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.GuardJoinDeviceAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.MobileListBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.AllMobileListContract;
import cn.treebear.kwifimanager.mvp.server.presenter.AllMobileListPresenter;

/**
 * @author Administrator
 */
public class ChooseControlMobileActivity extends BaseActivity<AllMobileListContract.Presenter, MobileListBean> implements AllMobileListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
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
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.choose_control_device, R.string.save);
        adapter = new GuardJoinDeviceAdapter(mobileList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mPresenter.getMobileList(MyApplication.getAppContext().getCurrentSelectNode(), pageNo);
    }

    @Override
    protected void onTitleRightClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Keys.PARENT_CONTROL_DEVICE, convert(adapter.getData()));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onTitleLeftClick();
    }

    private ArrayList<String> convert(List<MobileListBean.MobileBean> data) {
        ArrayList<String> result = new ArrayList<>();
        for (MobileListBean.MobileBean datum : data) {
            if (datum.getIsBlock() == 1) {
                result.add(datum.getMac());
            }
        }
        return result;
    }

    @Override
    public void onLoadData(MobileListBean resultData) {
        if (pageNo == 1) {
            mobileList.clear();
        }
        mobileList.addAll(resultData.getPage());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        super.onLoadFail(resultData, resultMsg, resultCode);
    }

    @Override
    public void onModifyMobileInfoResponse(BaseResponse response) {

    }
}
