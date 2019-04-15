package cn.treebear.kwifimanager.activity.bindap.settings;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.mvp.wifi.contract.ModifyWifiInfoContract;
import cn.treebear.kwifimanager.mvp.wifi.presenter.ModifyWifiInfoPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.NetWorkUtils;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * @author Administrator
 */
public class ModifyWifiInfoActivity extends BaseActivity<ModifyWifiInfoContract.Presenter, Object> implements ModifyWifiInfoContract.View {

    @BindView(R2.id.et_wifi_name)
    EditText etWifiName;
    @BindView(R2.id.tv_line_name)
    TextView tvLineName;
    @BindView(R2.id.et_wifi_password)
    EditText etWifiPassword;
    @BindView(R2.id.tv_line_password)
    TextView tvLinePassword;
    @BindView(R2.id.iv_password_eye)
    ImageView ivPasswordEye;
    private boolean passwordVisible = false;
    private TMessageDialog tMessageDialog;
    private WifiManager wifiManager;

    @Override
    public int layoutId() {
        return R.layout.activity_modify_wifi_info;
    }

    @Override
    public ModifyWifiInfoContract.Presenter getPresenter() {
        return new ModifyWifiInfoPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        listenFocus();
    }

    private void listenFocus() {
        etWifiName.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    etWifiName.setSelection(etWifiName.getText().length());
                    tvLineName.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    tvLineName.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etWifiPassword.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    etWifiPassword.setSelection(etWifiPassword.getText().length());
                    tvLinePassword.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    tvLinePassword.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
    }

    @OnClick(R2.id.iv_password_eye)
    public void onIvPasswordEyeClicked() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            ivPasswordEye.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            //显示明文--设置为可见的密码
            etWifiPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivPasswordEye.setImageResource(R.mipmap.ic_edit_eye_close_gray);
//            //显示密码--设置文本
            etWifiPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etWifiPassword.setSelection(etWifiPassword.getText().length());
    }

    @OnClick(R2.id.btn_confirm)
    public void onBtnConfirmClicked() {
        if (checkInput()) {
            initMessageDialog();
            tMessageDialog.doClick(new TMessageDialog.DoClickListener() {
                @Override
                public void onClickLeft(android.view.View view) {
                    dismiss(tMessageDialog);
                }

                @Override
                public void onClickRight(android.view.View view) {
                    mPresenter.modifyWifiInfo(NetWorkUtils.getSSIDWhenWifi(MyApplication.getAppContext()),
                            etWifiName.getText().toString(), etWifiPassword.getText().toString());
                    dismiss(tMessageDialog);
                    showLoading();
                }
            }).show();
        }
    }

    @Override
    public void onLoadData(Object resultData) {
        PermissionUtils.permission(PermissionConstants.LOCATION)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
//                        LoadingProgressDialog.showProgressDialog(ModifyWifiInfoActivity.this, getString(R.string.scanning));
//                        if (wifiManager != null) {
//                            wifiManager.setWifiEnabled(false);
//                            wifiManager.setWifiEnabled(true);
//                            wifiManager.startScan();
//                        }
                        MyApplication.getAppContext().getUser().setNodeSize(1);
                        ActivityStackUtils.finishAll(Config.Tags.TAG_FIRST_BIND_WIFI);
                    }

                    @Override
                    public void onDenied() {

                    }
                }).request();
//        etWifiName.postDelayed(() -> {
//            hideLoading();
//            if (NetWorkUtils.scanWifiByName(ModifyWifiInfoActivity.this, etWifiName.getText().toString())) {
//                ToastUtils.showShort(R.string.wifi_info_modify_success);
//            } else {
//                ToastUtils.showShort(R.string.set_success_scan_fail);
//            }
//
//        }, 3000);
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        super.onLoadFail(resultData, resultMsg, resultCode);
        WiFiHttpClient.dealWithResultCode(resultCode);
    }

    private boolean checkInput() {
        if (!Check.hasContent(etWifiName)) {
            ToastUtils.showShort(R.string.input_wifi_name_please);
            return false;
        }
        if (etWifiPassword.getText().length() < Config.Numbers.MIN_WIFI_PASSWORD) {
            ToastUtils.showShort(R.string.input_wifi_passowrd_please);
            return false;
        }
        return true;
    }

    private void initMessageDialog() {
        if (tMessageDialog == null) {
            tMessageDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.online_tips)
                    .content(R.string.modify_wifi_tips)
                    .left(R.string.cancel)
                    .right(R.string.confirm);
        }
    }

    @Override
    protected void onTitleLeftClick() {
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        finish();
    }
}
