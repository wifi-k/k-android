package net.treebear.kwifimanager.activity.account;

import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.contract.SetPasswordContract;
import net.treebear.kwifimanager.mvp.presenter.SetPasswordPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity<SetPasswordContract.ISetPasswordPresenter, Object> implements SetPasswordContract.ISetPasswordView {

    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.et_sign_up_password)
    EditText etSignUpPassword;
    @BindView(R.id.iv_password_state)
    ImageView ivPasswordState;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.line_phone)
    TextView linePhoneNumber;
    private boolean passwordVisible = false;

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
    public SetPasswordContract.ISetPasswordPresenter getPresenter() {
        return new SetPasswordPresenter();
    }
    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etSignUpPassword.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                    updateConfirmBtnEnable();
            }
        });
        etPasswordAgain.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateConfirmBtnEnable();
            }
        });
    }

    private void updateConfirmBtnEnable() {
        btnConfirm.setEnabled(etSignUpPassword.getText().length() >= Config.Numbers.MIN_PWD_LENGTH &&
                etPasswordAgain.getText().length() >= Config.Numbers.MIN_PWD_LENGTH);
    }


    /**
     * 配置EditText焦点变化监听
     */
    private void listenFocus() {
        etSignUpPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignUpPassword.setSelection(etSignUpPassword.getText().length());
                    linePhoneNumber.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePhoneNumber.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etPasswordAgain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etPasswordAgain.setSelection(etPasswordAgain.getText().length());
                    linePassword.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePassword.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
    }

    @OnClick(R.id.iv_password_state)
    public void onIvEditClearClicked() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            ivPasswordState.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            //显示明文--设置为可见的密码
            etSignUpPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//          etSignUpPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            ivPasswordState.setImageResource(R.mipmap.ic_edit_eye_close_gray);
//            //显示密码--设置文本
            etSignUpPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//            会改变字间距，故放弃此方法
//          etSignUpPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etSignUpPassword.setSelection(etSignUpPassword.getText().length());
    }

    @OnClick(R.id.btn_confirm)
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
        ActivityStackUtils.finishAll(Config.Tags.TAG_SIGN_ACCOUNT);
    }

    @Override
    protected void onTitleLeftClick() {
        ActivityStackUtils.popActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        finish();
    }

    @Override
    public void onBackPressed() {
        onTitleLeftClick();
    }
}
