package cn.treebear.kwifimanager.activity.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseTextWatcher;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.ForgetPasswordContract;
import cn.treebear.kwifimanager.mvp.server.presenter.ForgetPasswordPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;

/**
 * <h2>忘记密码</h2>
 * <li>获取验证码界面</li>
 * <li>重设密码界面(本页)</li>
 */
public class ForgetPwdSetActivity extends BaseActivity<ForgetPasswordContract.Presenter, BaseResponse> implements ForgetPasswordContract.View {

    @BindView(R2.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R2.id.line_password)
    TextView linePassword;
    @BindView(R2.id.et_sign_up_password)
    EditText etSignUpPassword;
    @BindView(R2.id.line_phone)
    TextView linePhone;
    @BindView(R2.id.iv_password_state)
    ImageView ivPasswordState;
    @BindView(R2.id.btn_confirm)
    Button btnConfirm;
    @BindView(R2.id.iv_password1_clear)
    ImageView ivPassword1Clear;
    @BindView(R2.id.iv_password2_eye)
    ImageView ivPassword2Eye;
    @BindView(R2.id.iv_password2_clear)
    ImageView ivPassword2Clear;
    private String mobile;
    private String vcode;
    private boolean password1Visible = false;

    @Override
    public int layoutId() {
        return R.layout.activity_forget_pwd_set;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            mobile = params.getString(Keys.MOBILE, Config.Text.EMPTY);
            vcode = params.getString(Keys.VERIFY_CODE, Config.Text.EMPTY);
        }
    }

    @Override
    public ForgetPasswordContract.Presenter getPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FORGET_PASSWORD, this);
        setTitleBack(R.string.find_password);
        listenFocus();
        listenTextChange();
    }

    @Override
    public void onLoadData(BaseResponse resultData) {
        ToastUtils.showShort(R.string.find_password_success);
        ActivityStackUtils.finishAll(Config.Tags.TAG_FORGET_PASSWORD);
    }

    @OnClick(R2.id.iv_password_state)
    public void onIvPasswordStateClicked() {
        password1Visible = !password1Visible;
        if (password1Visible) {
            ivPasswordState.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            etSignUpPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivPasswordState.setImageResource(R.mipmap.ic_edit_eye_close_gray);
            etSignUpPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etSignUpPassword.setSelection(etSignUpPassword.getText().length());
    }

    @OnClick(R2.id.iv_password1_clear)
    public void onIvPassword1ClearClicked() {
        etSignUpPassword.setText("");
    }

    @OnClick(R2.id.iv_password2_eye)
    public void onIvPassword2EyeClicked() {
        password1Visible = !password1Visible;
        if (password1Visible) {
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
        etPasswordAgain.setText("");
    }

    @OnClick(R2.id.btn_confirm)
    public void onBtnConfirmClicked() {
        if (!Check.equals(etSignUpPassword, etPasswordAgain)) {
            ToastUtils.showShort(R.string.password_not_equal);
            return;
        }
        mPresenter.setPassword(mobile, vcode, etSignUpPassword.getText().toString());
        btnConfirm.setEnabled(false);
    }

    @Override
    public void onLoadFail(BaseResponse data, String resultMsg, int resultCode) {
        super.onLoadFail(data, resultMsg, resultCode);
        btnConfirm.setEnabled(true);
        ToastUtils.showShort(R.string.message_error_check_retry);
    }

    /**
     * 监听输入框文字变化
     */
    private void listenTextChange() {
        etSignUpPassword.addTextChangedListener(new BaseTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                ivPassword1Clear.setVisibility(Check.hasContent(s) && etSignUpPassword.hasFocus() ? View.VISIBLE : View.GONE);
                ivPasswordState.setVisibility(Check.hasContent(s) && etSignUpPassword.hasFocus() ? View.VISIBLE : View.GONE);
                updateConfirmBtnEnable();
            }
        });
        etPasswordAgain.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivPassword2Clear.setVisibility(Check.hasContent(s) && etPasswordAgain.hasFocus() ? View.VISIBLE : View.GONE);
                ivPassword2Eye.setVisibility(Check.hasContent(s) && etPasswordAgain.hasFocus() ? View.VISIBLE : View.GONE);
                updateConfirmBtnEnable();
            }
        });
    }

    /**
     * 更新确认按键点击状态
     */
    private void updateConfirmBtnEnable() {
        btnConfirm.setEnabled(etSignUpPassword.getText().length() >= Config.Numbers.MIN_PWD_LENGTH &&
                etPasswordAgain.getText().length() >= Config.Numbers.MIN_PWD_LENGTH);
    }

    /**
     * 配置EditText焦点变化监听
     */
    private void listenFocus() {
        etSignUpPassword.setOnFocusChangeListener((v, hasFocus) -> {
            ivPassword1Clear.setVisibility(Check.hasContent(etSignUpPassword) && hasFocus ? View.VISIBLE : View.GONE);
            ivPasswordState.setVisibility(Check.hasContent(etSignUpPassword) && hasFocus ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etSignUpPassword.setSelection(etSignUpPassword.getText().length());
                linePhone.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePhone.setBackgroundColor(Config.Colors.LINE);
            }
        });
        etPasswordAgain.setOnFocusChangeListener((v, hasFocus) -> {
            ivPassword2Clear.setVisibility(Check.hasContent(etPasswordAgain) && hasFocus ? View.VISIBLE : View.GONE);
            ivPassword2Eye.setVisibility(Check.hasContent(etPasswordAgain) && hasFocus ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etPasswordAgain.setSelection(etPasswordAgain.getText().length());
                linePassword.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePassword.setBackgroundColor(Config.Colors.LINE);
            }
        });
    }


    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FORGET_PASSWORD, this);
    }

}
