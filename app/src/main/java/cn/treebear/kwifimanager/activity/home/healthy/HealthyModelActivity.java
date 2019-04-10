package cn.treebear.kwifimanager.activity.home.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.GsonBuilder;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
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
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * @author Administrator
 */
public class HealthyModelActivity extends BaseActivity<HealthyModelContract.Presenter, HealthyModelBean> implements HealthyModelContract.View {

    @BindView(R2.id.sb_healthy)
    SwitchButton sbHealthy;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_add_timer)
    TextView tvAddTimer;
    private ArrayList<HealthyModelBean.WifiBean.TimerBean> timeLimitList = new ArrayList<>();
    private HealthyModelAdapter healthyModelAdapter;
    private HealthyModelBean healthyModelInfo;
    private TMessageDialog unsavedDialog;
    private boolean hasModify;

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
            switch (view.getId()) {
                case R.id.iv_healthy_edit:
                    Bundle bundle = new Bundle();
                    bundle.putInt(Keys.POSITION, position);
                    bundle.putSerializable(Keys.TIME_LIMIT_BEAN, timeLimitList.get(position));
                    startActivityForResult(ModifyTimeActivity.class, bundle, Values.REQUEST_EDIT_TIME);
                    break;
                case R.id.iv_delete:
                    hasModify = true;
                    timeLimitList.remove(position);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });
    }

    @OnClick(R2.id.tv_add_timer)
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
        hasModify = false;
        ToastUtils.showShort(R.string.set_option_success);
        hideLoading();
        if (unsavedDialog.isShowing()) {
            dismiss(unsavedDialog);
            finish();
        }
    }

    @Override
    public void onSetInfoFailed(BaseResponse response) {
        ToastUtils.showShort(R.string.option_failed_retry);
        hideLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Values.REQUEST_EDIT_TIME && data != null) {
            hasModify = true;
            int position = data.getIntExtra(Keys.POSITION, -1);
            String startTime = data.getStringExtra(Keys.IT_START_TIME);
            String endTime = data.getStringExtra(Keys.IT_END_TIME);
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

    @Override
    protected void onTitleLeftClick() {
        if (hasModify) {
            showUnsavedDialog();
        } else {
            dismiss(unsavedDialog);
            super.onTitleLeftClick();
        }
    }

    private void showUnsavedDialog() {
        if (unsavedDialog == null) {
            unsavedDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.tips)
                    .content("您有修改的配置尚未保存，是否立即保存？")
                    .left("放弃")
                    .right("保存")
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            hasModify = false;
                            onTitleLeftClick();
                        }

                        @Override
                        public void onClickRight(View view) {
                            onTitleRightClick();
                        }
                    });
        }
        unsavedDialog.show();
    }

}
