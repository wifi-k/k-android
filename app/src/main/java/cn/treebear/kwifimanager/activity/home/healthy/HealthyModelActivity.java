package cn.treebear.kwifimanager.activity.home.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.GsonBuilder;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.home.time.ModifyTimeActivity;
import cn.treebear.kwifimanager.adapter.HealthyModelAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.HealthyModelBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.mvp.server.contract.HealthyModelContract;
import cn.treebear.kwifimanager.mvp.server.presenter.HealthyModelPresenter;
import cn.treebear.kwifimanager.util.Check;

/**
 * @author Administrator
 */
public class HealthyModelActivity extends BaseActivity<HealthyModelContract.Presenter, HealthyModelBean> implements HealthyModelContract.View {

    @BindView(R.id.sb_healthy)
    SwitchButton sbHealthy;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add_timer)
    TextView tvAddTimer;
    private ArrayList<HealthyModelBean.WifiBean.TimerBean> timeLimitList = new ArrayList<>();
    private HealthyModelAdapter healthyModelAdapter;
    private HealthyModelBean healthyModelInfo;

    @Override
    public int layoutId() {
        return R.layout.activity_healthy_model;
    }

    @Override
    public HealthyModelContract.Presenter getPresenter() {
        return new HealthyModelPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.healthy_model, R.string.save);
        mPresenter.getHealthyModelInfo(MyApplication.getAppContext().getCurrentSelectNode());
        sbHealthy.setOnCheckedChangeListener((view, isChecked) -> {
            recyclerView.setEnabled(isChecked);
            tvAddTimer.setEnabled(isChecked);
        });
        healthyModelAdapter = new HealthyModelAdapter(timeLimitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(healthyModelAdapter);
        healthyModelAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(Keys.POSITION, position);
            bundle.putSerializable(Keys.TIME_LIMIT_BEAN, timeLimitList.get(position));
            startActivity(ModifyTimeActivity.class, bundle);
        });
    }

    @OnClick(R.id.tv_add_timer)
    public void onTvAddTimerClicked() {
        startActivityForResult(ModifyTimeActivity.class, null, Values.REQUEST_EDIT_TIME);
    }

    @Override
    protected void onTitleRightClick() {
        showLoading(R.string.option_ing);
        ArrayList<HealthyModelBean.WifiBean> wifiBeans = new ArrayList<>();
        HealthyModelBean.WifiBean wifiBean = new HealthyModelBean.WifiBean();
        wifiBean.setFreq(0);
        wifiBean.setRssi(5);
        wifiBean.setTimer(timeLimitList);
        wifiBeans.add(wifiBean);
        mPresenter.setHealthyModelInfo(MyApplication.getAppContext().getCurrentSelectNode(), sbHealthy.isChecked() ? 1 : 0, wifiBeans);
    }

    @Override
    public void onLoadData(HealthyModelBean resultData) {
        healthyModelInfo = resultData;
        sbHealthy.setChecked(resultData.getOp() == 1);
        timeLimitList.clear();
        if (Check.hasContent(resultData.getWifi())) {
            HealthyModelBean.WifiBean wifiBean = new GsonBuilder().create().fromJson(resultData.getWifi().substring(1, resultData.getWifi().length() - 1), HealthyModelBean.WifiBean.class);
            if (wifiBean != null) {
                timeLimitList.addAll(wifiBean.getTimer());
            }
        }
        healthyModelAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSetInfoSuccess() {
        ToastUtils.showShort(R.string.set_option_success);
        finish();
    }

    @Override
    public void onSetInfoFailed(BaseResponse response) {
        ToastUtils.showShort(R.string.option_failed_retry);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Values.REQUEST_EDIT_TIME && data != null) {
            int position = data.getIntExtra(Keys.POSITION, -1);
            String startTime = data.getStringExtra(Keys.START_TIME);
            String endTime = data.getStringExtra(Keys.END_TIME);

            HealthyModelBean.WifiBean.TimerBean timerBean = new HealthyModelBean.WifiBean.TimerBean();
            timerBean.setStartTime(startTime);
            timerBean.setEndTime(endTime);
            if (position == -1 || position >= timeLimitList.size()) {
                timeLimitList.add(timerBean);
            } else {
                timeLimitList.set(position, timerBean);
            }
            healthyModelAdapter.notifyDataSetChanged();
        }
    }

}
