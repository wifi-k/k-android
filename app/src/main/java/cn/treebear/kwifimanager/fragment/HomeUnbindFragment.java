package cn.treebear.kwifimanager.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.activity.bindap.BindAction1Activity;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.mvp.server.contract.UnbindHomeContract;
import cn.treebear.kwifimanager.mvp.server.presenter.UnbindHomePresenter;
import cn.treebear.kwifimanager.util.NetWorkUtils;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;
import cn.treebear.kwifimanager.widget.dialog.TipsDialog;

/**
 * A simple {@link BaseFragment} subclass.
 *
 * @author Administrator
 */
public class HomeUnbindFragment extends BaseFragment<UnbindHomeContract.Presenter, BaseResponse> implements UnbindHomeContract.View {


    @BindView(R2.id.iv_bind)
    ImageView ivBind;
    @BindView(R2.id.tv_bind)
    TextView tvBind;
    @BindView(R2.id.tv_input_family_code)
    TextView tvInputFamilyCode;
    private TInputDialog inputDialog;
    private TipsDialog errorTipsDialog;
    private TipsDialog successTipsDialog;
    private TMessageDialog tMessageDialog;

    public HomeUnbindFragment() {

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_unbind;
    }

    @Override
    public UnbindHomeContract.Presenter getPresenter() {
        return new UnbindHomePresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.string.app_name, false);
    }

    private int count = 0;

    @OnClick({R.id.iv_bind, R.id.tv_bind})
    public void onViewClicked(View view) {
//        if (NetWorkUtils.isXiaoKSignIn()) {
            startActivity(BindAction1Activity.class);
//        } else if (!NetWorkUtils.isWifiConnected(mContext)) {
//            notXiaoKDialog();
//        } else {
//            if (count % 5 == 0) {
//                notXiaoKDialog();
//            } else {
//                ToastUtils.showShort("正在尝试与设备建立连接，请稍后");
//            }
//            count++;
//        }
    }

    @OnClick(R2.id.tv_input_family_code)
    public void onFamilyCodeClicked() {
        showFamilyCodeDialog();
    }

    private void showFamilyCodeDialog() {
        if (inputDialog == null) {
            inputDialog = new TInputDialog(mContext);
            inputDialog.setTitle(R.string.input_family_code_into_family);
            inputDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {

                @Override
                public void onLeftClick(String s) {
                    dismiss(inputDialog);
                }

                @Override
                public void onRightClick(String s) {
                    mPresenter.joinFamily(s.trim());
                }
            });
        }
        inputDialog.show();
    }

    @Override
    public void onLoadData(BaseResponse resultData) {
        dismiss(inputDialog);
        showSuccessTips();
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        dismiss(inputDialog);
        showErrorTips();
    }

    private void showErrorTips() {
        if (errorTipsDialog == null) {
            errorTipsDialog = new TipsDialog(mContext).icon(R.mipmap.ic_tips_warnning)
                    .title(R.string.family_code_error)
                    .content(R.string.family_code_error_tips)
                    .oneButtonRight()
                    .right(R.string.input_again)
                    .doClick(new TipsDialog.DoClickListener() {
                        @Override
                        public void onClickRight(TextView tvRight) {
                            dismiss(errorTipsDialog);
                            showFamilyCodeDialog();
                        }
                    });
        }
        errorTipsDialog.show();
    }

    private void showSuccessTips() {
        if (successTipsDialog == null) {
            successTipsDialog = new TipsDialog(mContext).icon(R.mipmap.ic_tips_success)
                    .title(R.string.family_code_ok)
                    .noContent()
                    .oneButtonRight()
                    .right(R.string.confirm)
                    .doClick(new TipsDialog.DoClickListener() {
                        @Override
                        public void onClickRight(TextView tvRight) {
                            MyApplication.getAppContext().getUser().setNodeSize(1);
                            if (mContext instanceof MainActivity) {
                                ((MainActivity) mContext).updateHomeFragment();
                            }
                            dismiss(successTipsDialog);
                        }
                    });
        }
        successTipsDialog.show();
    }

    private void notXiaoKDialog() {
        initMessageDialog();
        tMessageDialog.content("请前往WiFi设置连接名称为“xiaok-xxxx”的WiFi，然后再绑定设备。")
                .doClick(new TMessageDialog.DoClickListener() {
                    @Override
                    public void onClickLeft(android.view.View view) {
                        dismiss(tMessageDialog);
                    }

                    @Override
                    public void onClickRight(android.view.View view) {
                        NetWorkUtils.gotoWifiSetting(mContext);
                        dismiss(tMessageDialog);
                    }
                }).show();
    }

    /**
     * 初始化通用弹窗
     */
    private void initMessageDialog() {
        if (tMessageDialog == null) {
            tMessageDialog = new TMessageDialog(mContext).withoutMid()
                    .title(R.string.online_tips)
                    .content("")
                    .left(R.string.cancel)
                    .right(R.string.confirm)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(android.view.View view) {
                            dismiss(tMessageDialog);
                        }

                        @Override
                        public void onClickRight(android.view.View view) {
                            dismiss(tMessageDialog);
                        }
                    });
        }
    }


    @Override
    public void onDestroy() {
        dismiss(successTipsDialog, errorTipsDialog, inputDialog, tMessageDialog);
        super.onDestroy();
    }
}
