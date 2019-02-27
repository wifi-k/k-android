package net.treebear.kwifimanager.activity.account;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.contract.SignUpVerifyContract;
import net.treebear.kwifimanager.mvp.presenter.SignUpVerifyPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.CountObserver;
import net.treebear.kwifimanager.util.CountUtil;
import net.treebear.kwifimanager.widget.TMessageDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * <h2>注册页</h2>
 * <ul>
 * <li>输入手机号</li>
 * <li>获取验证码</li>
 * <li>输入验证码</li>
 * <li>点击注册</li>
 * </ul>
 */
public class SignUpActivity extends BaseActivity<SignUpVerifyContract.ISignUpVerifyPresenter, String> implements SignUpVerifyContract.ISignUpVerifyView {

    @BindView(R.id.et_sign_up_verify)
    EditText etSignUpCode;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.et_sign_up_phone)
    EditText etSignUpPhone;
    @BindView(R.id.line_phone_number)
    TextView linePhoneNumber;
    @BindView(R.id.iv_edit_clear)
    ImageView ivEditClear;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_sign_next)
    TextView tvSignNext;
    /**
     * 定时器订阅者
     */
    private Disposable mCountDisposable;
    private TMessageDialog signDialog;
    private String mVerifyCode;

    @Override
    public int layoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public SignUpVerifyContract.ISignUpVerifyPresenter getPresenter() {
        return new SignUpVerifyPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        statusWhiteFontBlack();
        setTitleBack("");
        listenFocus();
        listenTextChange();
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        etSignUpPhone.setText("");
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        // TODO: 2019/2/27 考虑：本地验证手机号合法性
        if (etSignUpPhone.getText().length() == 11) {
            mPresenter.getSignUpVerifyCode(etSignUpPhone.getText().toString());
            tvGetCode.setEnabled(false);
        }
    }

    @Override
    public void onLoadData(String resultData) {
        super.onLoadData(resultData);
        mVerifyCode = resultData;
        countDown();
    }

    @Override
    public void onLoadFail(String resultMsg, int resultCode) {
//        super.onLoadFail(resultMsg, resultCode);
        initSignMessageDialog();
        signDialog.show();
    }

    @OnClick(R.id.tv_sign_next)
    public void onTvSignNextClicked() {
        // TODO: 2019/2/27 发送注册请求,成功响应后跳转
        // 测试已有账号弹窗样式
        // initSignMessageDialog();
        // signDialog.show();
        mPresenter.signUpByVerifyCode(etSignUpPhone.getText().toString(),
                etSignUpCode.getText().toString());
    }

    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etSignUpPhone.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivEditClear.setVisibility(Check.hasContent(s) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                ivEditClear.setVisibility(Check.hasContent(s) ? View.VISIBLE : View.GONE);
                if (s.length() == Config.Numbers.PHONE_LENGTH) {
                    // TODO: 2019/2/26 检查手机号合法性
                    tvGetCode.setTextColor(Config.Colors.MAIN);
                    updateConfirmBtnEnable();
                }
            }
        });
        etSignUpCode.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateConfirmBtnEnable();
            }
        });
    }

    private void updateConfirmBtnEnable() {
        tvSignNext.setEnabled(etSignUpPhone.getText().length() == Config.Numbers.PHONE_LENGTH &&
                etSignUpCode.getText().length() == Config.Numbers.VERIFY_CODE_LENGTH);
    }

    /**
     * 配置EditText焦点变化监听
     */
    private void listenFocus() {
        etSignUpPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignUpPhone.setSelection(etSignUpPhone.getText().length());
                    linePhoneNumber.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePhoneNumber.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etSignUpCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignUpCode.setSelection(etSignUpCode.getText().length());
                    linePassword.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePassword.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
    }

    /**
     * 使用前初始化弹窗
     */
    private void initSignMessageDialog() {
        if (signDialog == null) {
            signDialog = new TMessageDialog(this).withoutMid()
                    .title(Config.Strings.TIPS)
                    .content(Config.Strings.HAS_ACCOUNT)
                    .left(Config.Strings.CANCEL)
                    .right(Config.Strings.SIGN_IN_NOW)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            signDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            startActivity(SignInActivity.class);
//                            startActivity(SetPasswordActivity.class);
                            signDialog.dismiss();
                            finish();
                        }
                    });
        }
    }

    /**
     * 短信发送成功时开始计时
     */
    private void countDown() {
        CountUtil.numberDown(Config.Numbers.VERIFY_CODE_WAIT_TIME, new CountObserver() {

            @Override
            public void onSubscribe(Disposable d) {
                mCountDisposable = d;
            }

            @Override
            public void onNext(Long t) {
                if (t <= 0) {
                    tvGetCode.setText(Config.Strings.GET_VERIFY_CODE);
                } else {
                    tvGetCode.setText(String.format(Config.Strings.VERIFY_CODE_FORMAT, t));
                }
            }

            @Override
            public void onComplete() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText(Config.Strings.GET_VERIFY_CODE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        dispose(mCountDisposable);
        super.onDestroy();
    }

    @Override
    public void onSignUpOk(UserInfoBean bean) {
        MyApplication.getAppContext().savedUser(bean);
        dispose(mCountDisposable);
        startActivity(SetPasswordActivity.class);
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
