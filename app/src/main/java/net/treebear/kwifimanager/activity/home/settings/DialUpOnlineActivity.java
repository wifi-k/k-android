package net.treebear.kwifimanager.activity.home.settings;

import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.bean.WifiDeviceInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.wifi.contract.DialUpContract;
import net.treebear.kwifimanager.mvp.wifi.presenter.DialUpPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class DialUpOnlineActivity extends BaseActivity<DialUpContract.IDialUpPresenter, WifiDeviceInfo> implements DialUpContract.IDialUpView {

    @BindView(R.id.et_net_account)
    EditText etNetAccount;
    @BindView(R.id.line_account)
    TextView lineAccount;
    @BindView(R.id.et_net_passowrd)
    EditText etNetPassowrd;
    @BindView(R.id.iv_password_ryr)
    ImageView ivPasswordRyr;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.btn_dial_up_confirm)
    Button btnDialUpConfirm;
    private boolean passwordVisible = false;
    private int count = 0;

    @Override
    public int layoutId() {
        return R.layout.activity_dial_up_online;
    }

    @Override
    public DialUpContract.IDialUpPresenter getPresenter() {
        return new DialUpPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        setTitleBack(R.string.setting);
        listenFocus();
        listenInput();
    }

    private void listenInput() {
        BaseTextWatcher textWatcher = new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                btnDialUpConfirm.setEnabled(Check.hasContent(etNetAccount) && Check.hasContent(etNetPassowrd));
            }
        };
        etNetAccount.addTextChangedListener(textWatcher);
        etNetPassowrd.addTextChangedListener(textWatcher);
    }

    private void listenFocus() {
        etNetAccount.setOnFocusChangeListener((v, hasFocus) -> lineAccount.setBackgroundColor(hasFocus ? Config.Colors.MAIN : Config.Colors.LINE));
        etNetPassowrd.setOnFocusChangeListener((v, hasFocus) -> linePassword.setBackgroundColor(hasFocus ? Config.Colors.MAIN : Config.Colors.LINE));
    }

    @OnClick(R.id.iv_password_ryr)
    public void onIvPasswordRyrClicked() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            ivPasswordRyr.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            //显示明文--设置为可见的密码
            etNetPassowrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivPasswordRyr.setImageResource(R.mipmap.ic_edit_eye_close_gray);
//            //显示密码--设置文本
            etNetPassowrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etNetPassowrd.setSelection(etNetPassowrd.getText().length());
    }

    @OnClick(R.id.btn_dial_up_confirm)
    public void onBtnDialUpConfirmClicked() {
        showLoading();
        count = 0;
        mPresenter.dialUpSet(etNetAccount.getText().toString(), etNetPassowrd.getText().toString());
    }

    @Override
    public void onLoadData(WifiDeviceInfo resultData) {
        WifiDeviceInfo deviceInfo = MyApplication.getAppContext().getDeviceInfo();
        deviceInfo.setConnect(true);
        deviceInfo.setWan(resultData.getWan());
        MyApplication.getAppContext().saveDeviceInfo(deviceInfo);
        hideLoading();
        startActivity(ModifyWifiInfoActivity.class);
        ToastUtils.showShort(R.string.connect_success);
    }

    @Override
    public void onLoadFail(String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                    return;
                }
                btnDialUpConfirm.postDelayed(() -> {
                    if (mPresenter != null) {
                        mPresenter.queryNetStatus();
                    }
                }, 2000);
                break;
            case Config.WifiResponseCode.CONNECT_SUCCESS:
                hideLoading();
                startActivity(ModifyWifiInfoActivity.class);
                ToastUtils.showShort(R.string.connect_success);
                break;
            default:
                hideLoading();
                ToastUtils.showShort(R.string.connect_fail);
                // 延时1秒再次查询
//                btnDialUpConfirm.postDelayed(() -> mPresenter.queryNetStatus(), 1000);
                break;
        }
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        mPresenter = null;
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}
