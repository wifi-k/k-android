package cn.treebear.kwifimanager.activity.home.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.suke.widget.SwitchButton;

import java.util.ArrayList;

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
import cn.treebear.kwifimanager.util.TLog;

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
            TLog.w(macs);
        }
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.choose_control_device, R.string.save);
        adapter = new GuardJoinDeviceAdapter(mobileList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mPresenter.getMobileList(MyApplication.getAppContext().getCurrentSelectNode(), pageNo);
        adapter.setOnCheckedChangeListener(new GuardJoinDeviceAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isCheck, MobileListBean.MobileBean item) {
                if (isCheck) {
                    if (!macs.contains(item.getMac())) {
                        macs.add(item.getMac());
                    }
                } else {
                    macs.remove(item.getMac());
                }
            }
        });
    }

    @Override
    protected void onTitleRightClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Keys.PARENT_CONTROL_DEVICE, macs);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onTitleLeftClick();
    }

    @Override
    public void onLoadData(MobileListBean resultData) {
        if (pageNo == 1) {
            mobileList.clear();
        }
        mobileList.addAll(resultData.getPage());
        for (MobileListBean.MobileBean bean : mobileList) {
            for (String mac : macs) {
                if (bean.getMac().equals(mac)){
                    bean.setIsBlock(1);
                }
            }
        }
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
