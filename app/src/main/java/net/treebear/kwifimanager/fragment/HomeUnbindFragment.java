package net.treebear.kwifimanager.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.MainActivity;
import net.treebear.kwifimanager.activity.bindap.BindAction1Activity;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.config.Values;
import net.treebear.kwifimanager.util.NetWorkUtils;
import net.treebear.kwifimanager.widget.dialog.TInputDialog;
import net.treebear.kwifimanager.widget.dialog.TipsDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link BaseFragment} subclass.
 *
 * @author Administrator
 */
public class HomeUnbindFragment extends BaseFragment {


    @BindView(R.id.iv_bind)
    ImageView ivBind;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_input_family_code)
    TextView tvInputFamilyCode;
    private TInputDialog inputDialog;
    private TipsDialog errorTipsDialog;
    private TipsDialog successTipsDialog;

    public HomeUnbindFragment() {

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_unbind;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.string.app_name, false);
    }

    @OnClick({R.id.iv_bind, R.id.tv_bind})
    public void onViewClicked(View view) {
        String wifiSSID = NetWorkUtils.getSSIDWhenWifi(mContext);
        Bundle bundle = new Bundle();
        if (NetWorkUtils.isWifiConnected(mContext)) {
            if (wifiSSID.startsWith(Config.Text.AP_NAME_START)) {
                bundle.putInt(Keys.TYPE, Values.CONNET_WIFI_XIAOK);
            } else {
                bundle.putInt(Keys.TYPE, Values.CONNECT_WIFI_OTHER);
            }
        } else {
            bundle.putInt(Keys.TYPE, Values.CONNECT_WIFI_NONE);
        }
        startActivity(BindAction1Activity.class, bundle);
    }

    @OnClick(R.id.tv_input_family_code)
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
                    inputDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/3/7 上传判断家庭码
                    inputDialog.dismiss();
                    if ("1234".equals(s)) {
                        showSuccessTips();
                    } else {
                        showErrorTips();
                    }
                }
            });
        }
        inputDialog.show();
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
                            errorTipsDialog.dismiss();
                            showFamilyCodeDialog();
                        }
                    });
        }
        errorTipsDialog.show();
    }

    private void showSuccessTips() {
        if (successTipsDialog == null) {
            successTipsDialog = new TipsDialog(mContext).icon(R.mipmap.ic_tips_warnning)
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
                            successTipsDialog.dismiss();
                        }
                    });
        }
        successTipsDialog.show();
    }

    @Override
    public void onDestroy() {
        dismiss(successTipsDialog, errorTipsDialog, inputDialog);
        super.onDestroy();
    }
}
