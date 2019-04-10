package cn.treebear.kwifimanager.activity.account;

import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseTextWatcher;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.server.contract.SetPasswordContract;
import cn.treebear.kwifimanager.mvp.server.presenter.SetPasswordPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;

/**
 * <h2>注册后设置密码</h2>
 * <p>逻辑上可优化，此项在当前版本(V1.0)可跳过，逻辑上不合理</p>
 */
public class SetPasswordActivity extends BaseActivity<SetPasswordContract.Presenter, Object> implements SetPasswordContract.View {

    @BindView(R2.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R2.id.et_sign_up_password)
    EditText etSignUpPassword;
    @BindView(R2.id.iv_password_state)
    ImageView ivPasswordState;
    @BindView(R2.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R2.id.line_password)
    TextView linePassword;
    @BindView(R2.id.line_phone)
    TextView linePhoneNumber;
    @BindView(R2.id.iv_password1_clear)
    ImageView ivPassword1Clear;
    @BindView(R2.id.iv_password2_eye)
    ImageView ivPassword2Eye;
    @BindView(R2.id.iv_password2_clear)
    ImageView ivPassword2Clear;
    /**
     * password 明文/密码状态
     */
    private boolean password1Visible = false;
    private boolean isModifyPassword;
    private boolean password2Visible = false;

    @Override
    public int layoutId() {
        return R.layout.activity_set_password;
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        statusWhiteFontBlack();
        setTitleBack(Config.Text.EMPTY);
        listenFocus();
        listenTextChange();
    }

    @Override
    public SetPasswordContract.Presenter getPresenter() {
        return new SetPasswordPresenter();
    }

    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etSignUpPassword.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivPassword1Clear.setVisibility(etSignUpPassword.hasFocus() && Check.hasContent(s) ? View.VISIBLE : View.GONE);
                ivPasswordState.setVisibility(etSignUpPassword.hasFocus() && Check.hasContent(s) ? View.VISIBLE : View.GONE);
                updateConfirmBtnEnable();
            }
        });
        etPasswordAgain.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivPassword2Clear.setVisibility(etPasswordAgain.hasFocus() && Check.hasContent(s) ? View.VISIBLE : View.GONE);
                ivPassword2Eye.setVisibility(etPasswordAgain.hasFocus() && Check.hasContent(s) ? View.VISIBLE : View.GONE);
                updateConfirmBtnEnable();
            }
        });
    }

    /**
     * 更新确认按键可点击状态
     */
    private void updateConfirmBtnEnable() {
        btnConfirm.setEnabled(etSignUpPassword.getText().length() >= Config.Numbers.MIN_PWD_LENGTH &&
                etPasswordAgain.getText().length() >= Config.Numbers.MIN_PWD_LENGTH);
    }


    /**
     * 配置EditText焦点变化监听
     */
    private void listenFocus() {
        etSignUpPassword.requestFocus();
        etSignUpPassword.setOnFocusChangeListener((v, hasFocus) -> {
            ivPassword1Clear.setVisibility(hasFocus && Check.hasContent(etSignUpPassword) ? View.VISIBLE : View.GONE);
            ivPasswordState.setVisibility(hasFocus && Check.hasContent(etSignUpPassword) ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etSignUpPassword.setSelection(etSignUpPassword.getText().length());
                linePhoneNumber.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePhoneNumber.setBackgroundColor(Config.Colors.LINE);
            }
        });
        etPasswordAgain.setOnFocusChangeListener((v, hasFocus) -> {
            ivPassword2Clear.setVisibility(hasFocus && Check.hasContent(etPasswordAgain) ? View.VISIBLE : View.GONE);
            ivPassword2Eye.setVisibility(hasFocus && Check.hasContent(etPasswordAgain) ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etPasswordAgain.setSelection(etPasswordAgain.getText().length());
                linePassword.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePassword.setBackgroundColor(Config.Colors.LINE);
            }
        });
    }

    @OnClick(R2.id.iv_password_state)
    public void onIvEditClearClicked() {
        password1Visible = !password1Visible;
        if (password1Visible) {
            ivPasswordState.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            etSignUpPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivPasswordState.setImageResource(R.mipmap.ic_edit_eye_close_main);
            etSignUpPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etSignUpPassword.setSelection(etSignUpPassword.getText().length());
    }

    @OnClick(R2.id.iv_password1_clear)
    public void onIvPassword1ClearClicked() {
        etPasswordAgain.setText("");
    }

    @OnClick(R2.id.iv_password2_eye)
    public void onIvPassword2EyeClicked() {
        password2Visible = !password2Visible;
        if (password2Visible) {
            ivPassword2Eye.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            etPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivPassword2Eye.setImageResource(R.mipmap.ic_edit_eye_close_main);
            etPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etPasswordAgain.setSelection(etPasswordAgain.getText().length());
    }

    @OnClick(R2.id.iv_password2_clear)
    public void onIvPassword2ClearClicked() {
    }

    @OnClick(R2.id.btn_confirm)
    public void onBtnConfirmClicked() {
        if (Check.equals(etSignUpPassword, etPasswordAgain)) {
            mPresenter.setPassword(etSignUpPassword.getText().toString());
        } else {
            ToastUtils.showShort(R.string.password_not_equal);
        }
        // TEST 测试关闭栈中界面
//        startActivity(MainActivity.class);
//        ActivityStackUtils.finishAll(Config.Keys.TAG_SIGN_ACCOUNT);
    }

    @Override
    public void onLoadData(Object resultData) {
        ToastUtils.showShort(R.string.set_password_success);
        MyApplication.getAppContext().setNeedUpdateUserInfo(true);
        startActivity(MainActivity.class);
        ActivityStackUtils.finishAll(Config.Tags.TAG_SIGN_ACCOUNT);
        ActivityStackUtils.finishAll(Config.Tags.TAG_LAUNCH_ROOT);
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        super.onLoadFail(resultData, resultMsg, resultCode);
        ToastUtils.showShort(R.string.message_error_check_retry);
    }

    @Override
    protected void onTitleLeftClick() {
        ActivityStackUtils.popActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        finish();
    }

}
