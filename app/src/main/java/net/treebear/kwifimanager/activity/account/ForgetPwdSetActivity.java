package net.treebear.kwifimanager.activity.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.ForgetPasswordContract;
import net.treebear.kwifimanager.mvp.server.presenter.ForgetPasswordPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <h2>忘记密码</h2>
 * <li>获取验证码界面</li>
 * <li>重设密码界面(本页)</li>
 */
public class ForgetPwdSetActivity extends BaseActivity<ForgetPasswordContract.Presenter, BaseResponse> implements ForgetPasswordContract.View {

    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.et_sign_up_password)
    EditText etSignUpPassword;
    @BindView(R.id.line_phone)
    TextView linePhone;
    @BindView(R.id.iv_password_state)
    ImageView ivPasswordState;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private String mobile;
    private String vcode;
    private boolean passwordVisible = false;

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

    @OnClick(R.id.iv_password_state)
    public void onIvPasswordStateClicked() {
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
        if (!Check.equals(etSignUpPassword, etPasswordAgain)) {
            ToastUtils.showShort(R.string.password_not_equal);
            return;
        }
        mPresenter.setPassword(mobile, vcode, etSignUpPassword.getText().toString());
        btnConfirm.setEnabled(false);
    }

    @Override
    public void onLoadFail(BaseResponse data,String resultMsg, int resultCode) {
        super.onLoadFail(data,resultMsg, resultCode);
        ToastUtils.showShort(resultMsg);
    }

    /**
     * 监听输入框文字变化
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
        etSignUpPassword.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignUpPassword.setSelection(etSignUpPassword.getText().length());
                    linePhone.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePhone.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etPasswordAgain.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    etPasswordAgain.setSelection(etPasswordAgain.getText().length());
                    linePassword.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePassword.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FORGET_PASSWORD, this);
    }
}
