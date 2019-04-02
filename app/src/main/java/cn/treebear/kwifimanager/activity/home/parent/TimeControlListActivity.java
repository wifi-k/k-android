package cn.treebear.kwifimanager.activity.home.parent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.adapter.BanTimeAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.TimeControlbean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.mvp.server.contract.TimeControlContract;
import cn.treebear.kwifimanager.mvp.server.presenter.TimeControlPresenter;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * @author Administrator
 */
public class TimeControlListActivity extends BaseActivity<TimeControlContract.Presenter, TimeControlbean> implements TimeControlContract.View {

    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.rv_ban_app)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add_ban_app_plan)
    TextView tvAddTimeControl;
    private ArrayList<TimeControlbean.TimeControl> timeLimitList = new ArrayList<>();
    private int currentModifyPosition;
    private TInputDialog tInputDialog;
    private BanTimeAdapter banTimeAdapter;
    private TMessageDialog deleteDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_ban_app_list;
    }

    @Override
    public TimeControlContract.Presenter getPresenter() {
        return new TimeControlPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.online_time_control);
        tvTips.setText(R.string.ban_time_tips);
        tvAddTimeControl.setText(R.string.increase_new_time_control);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        banTimeAdapter = new BanTimeAdapter(timeLimitList);
        recyclerView.setAdapter(banTimeAdapter);
        banTimeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            currentModifyPosition = position;
            switch (view.getId()) {
                case R.id.iv_ban_plan_edit:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Keys.BAN_APP_PLAN, timeLimitList.get(position));
                    startActivity(TimeControlPlanActivity.class, bundle);
                    break;
                case R.id.iv_ban_plan_delete:
                    showDeleteDialog();
                    break;
                default:
                    break;
            }
        });
        banTimeAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Keys.BAN_APP_PLAN, timeLimitList.get(position));
            startActivity(TimeControlPlanActivity.class, bundle);
        });
    }

    private void showDeleteDialog() {
        if (deleteDialog == null) {
            deleteDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.tips)
                    .content("确认删除时间控制计划？")
                    .left(R.string.cancel)
                    .right(R.string.confirm)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            super.onClickLeft(view);
                        }

                        @Override
                        public void onClickRight(View view) {
                            mPresenter.deleteTimeControlPlan(MyApplication.getAppContext().getCurrentSelectNode(), timeLimitList.get(currentModifyPosition).getId());
                        }
                    });
        }
        deleteDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getTimeControlPlan(MyApplication.getAppContext().getCurrentSelectNode());
    }

    @OnClick(R.id.tv_add_ban_app_plan)
    public void onTvAddNewTCClick() {
        startActivity(TimeControlPlanActivity.class);
    }

    /**
     * 修改名称弹窗
     */
    private void resetInputDialog() {
        if (tInputDialog == null) {
            tInputDialog = new TInputDialog(this);
            tInputDialog.setTitle(R.string.modify_name);
            tInputDialog.setEditHint(R.string.input_new_name_please);
            tInputDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {
                @Override
                public void onLeftClick(String s) {
                    tInputDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    timeLimitList.get(currentModifyPosition).setName(s);
                    tInputDialog.dismiss();
                    banTimeAdapter.notifyDataSetChanged();
                }
            });
        }
        tInputDialog.clearInputText();
    }

    @Override
    protected void onDestroy() {
        dismiss(tInputDialog, deleteDialog);
        super.onDestroy();
    }

    @Override
    public void onLoadData(TimeControlbean resultData) {
        hideLoading();
        if (resultData == null) {
            return;
        }
        timeLimitList.clear();
        timeLimitList.addAll(resultData.getPage());
        banTimeAdapter.notifyDataSetChanged();
    }

//    private void dealData(TimeControlbean resultData) {
//            List<TimeControlbean.TimeControl> page = resultData.getPage();
//            for (TimeControlbean.TimeControl control : page) {
//                String mac = control.getMac();
//                if (Check.maxThen(mac, 2)) {
//                    try {
//                    ArrayList<String> m = new ArrayList<>();
//                    JSONArray jsonArray = new JSONArray(mac);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        m.add(String.valueOf(jsonArray.get(i)));
//                    }
//                        TLog.i(m);
//                        control.setsMac(m);
//                    } catch (JSONException e) {
//                        TLog.e(e);
//                    }
//                }
//            }
//            timeLimitList.clear();
//            timeLimitList.addAll(page);
//            banTimeAdapter.notifyDataSetChanged();
//
//    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        super.onLoadFail(resultData, resultMsg, resultCode);
        TLog.i(resultMsg);
        ToastUtils.showShort(R.string.get_option_failed);
    }

    @Override
    public void onSetAllowTimeResponse(BaseResponse response) {
        switch (response.getCode()) {
            case ApiCode.SUCC:
                ToastUtils.showShort(R.string.modify_success);
                break;
            default:
                ToastUtils.showShort(R.string.modify_failed_retry);
                break;
        }
    }

    @Override
    public void onDeleteAllowTimeResponse(BaseResponse response) {
        switch (response.getCode()) {
            case ApiCode.SUCC:
                timeLimitList.remove(currentModifyPosition);
                banTimeAdapter.notifyDataSetChanged();
                dismiss(deleteDialog);
                ToastUtils.showShort(R.string.delete_success);
                break;
            default:
                ToastUtils.showShort(R.string.delete_failed_retry);
                break;
        }
    }
}
