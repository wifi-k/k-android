package cn.treebear.kwifimanager.activity.bindap.settings;

import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseTextWatcher;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.wifi.contract.DialUpContract;
import cn.treebear.kwifimanager.mvp.wifi.presenter.DialUpPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;

/**
 * @author Administrator
 */
public class DialUpOnlineActivity extends BaseActivity<DialUpContract.Presenter, WifiDeviceInfo> implements DialUpContract.View {

    @BindView(R2.id.et_net_account)
    EditText etNetAccount;
    @BindView(R2.id.line_account)
    TextView lineAccount;
    @BindView(R2.id.et_net_passowrd)
    EditText etNetPassowrd;
    @BindView(R2.id.iv_password_ryr)
    ImageView ivPasswordRyr;
    @BindView(R2.id.line_password)
    TextView linePassword;
    @BindView(R2.id.btn_dial_up_confirm)
    Button btnDialUpConfirm;
    private boolean passwordVisible = false;
    private int count = 0;

    @Override
    public int layoutId() {
        return R.layout.activity_dial_up_online;
    }

    @Override
    public DialUpContract.Presenter getPresenter() {
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

    @OnClick(R2.id.iv_password_ryr)
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

    @OnClick(R2.id.btn_dial_up_confirm)
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
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
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
