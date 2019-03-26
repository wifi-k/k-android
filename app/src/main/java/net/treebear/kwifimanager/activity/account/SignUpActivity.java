package net.treebear.kwifimanager.activity.account;

import android.text.Editable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.server.contract.SignUpVerifyContract;
import net.treebear.kwifimanager.mvp.server.presenter.SignUpVerifyPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.CountObserver;
import net.treebear.kwifimanager.util.CountUtil;
import net.treebear.kwifimanager.util.UserInfoUtil;
import net.treebear.kwifimanager.widget.dialog.TMessageDialog;

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
public class SignUpActivity extends BaseActivity<SignUpVerifyContract.Presenter, String> implements SignUpVerifyContract.View {

    @BindView(R.id.et_verify)
    EditText etSignUpCode;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.et_phone)
    EditText etSignUpPhone;
    @BindView(R.id.line_phone)
    TextView linePhoneNumber;
    @BindView(R.id.iv_edit_clear)
    ImageView ivEditClear;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_sign_next)
    TextView tvSignNext;
    @BindView(R.id.cb_signup_protocol)
    CheckBox cbSignupProtocol;
    /**
     * 定时器订阅者
     */
    private Disposable mCountDisposable;
    /**
     * 提示弹窗
     */
    private TMessageDialog signDialog;
    /**
     * 系统下发的验证码，用于本地验证
     */
    private String mVerifyCode;

    @Override
    public int layoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public SignUpVerifyContract.Presenter getPresenter() {
        return new SignUpVerifyPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        statusWhiteFontBlack();
        setTitleBack(Config.Text.EMPTY);
        listenFocus();
        listenTextChange();
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        etSignUpPhone.setText("");
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        if (etSignUpPhone.getText().length() == 11) {
            mPresenter.getSignUpVerifyCode(etSignUpPhone.getText().toString());
            tvGetCode.setEnabled(false);
        }
    }

    @Override
    public void onLoadData(String resultData) {
        mVerifyCode = resultData;
        ToastUtils.showShort(Config.Tips.VERIFY_SMS_SEND);
        countDown();
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.ServerResponseCode.HAS_SIGN_UP:
                initSignMessageDialog();
                signDialog.show();
                break;
            default:
                ToastUtils.showShort(resultMsg);
                break;
        }
    }

    @OnClick(R.id.tv_sign_next)
    public void onTvSignNextClicked() {
        if (!etSignUpCode.getText().toString().equals(mVerifyCode)) {
            ToastUtils.showShort(Config.Tips.VERIFY_CODE_ERROR);
            return;
        }
        if (cbSignupProtocol.isChecked()) {
            mPresenter.signUpByVerifyCode(etSignUpPhone.getText().toString(),
                    etSignUpCode.getText().toString());
        } else {
            ToastUtils.showShort(Config.Tips.READ_AND_AGREE_PROTOCOL);
        }
    }

    @OnClick(R.id.tv_user_protocol)
    public void onTvUserProtocolClicked() {
        openWebsite(Config.Urls.USER_PROTOCOL);
        cbSignupProtocol.setChecked(true);
    }

    @OnClick(R.id.tv_private_protocol)
    public void onTvPrivateProtocolClicked() {
        openWebsite(Config.Urls.PRIVATE_PROTOCOL);
        cbSignupProtocol.setChecked(true);
    }

    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etSignUpPhone.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivEditClear.setVisibility(Check.hasContent(s) ? android.view.View.VISIBLE : android.view.View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                ivEditClear.setVisibility(Check.hasContent(s) ? android.view.View.VISIBLE : android.view.View.GONE);
                if (s.length() == Config.Numbers.PHONE_LENGTH) {
                    // TODO: 2019/2/26 检查手机号合法性
                    tvGetCode.setTextColor(Config.Colors.MAIN);
                    updateConfirmBtnEnable();
                } else {
                    tvGetCode.setTextColor(Config.Colors.TEXT_9B);
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

    /**
     * 更新确认按键可点击状态
     */
    private void updateConfirmBtnEnable() {
        tvSignNext.setEnabled(etSignUpPhone.getText().length() == Config.Numbers.PHONE_LENGTH &&
                etSignUpCode.getText().length() == Config.Numbers.VERIFY_CODE_LENGTH);
    }

    /**
     * 配置EditText焦点变化监听
     */
    private void listenFocus() {
        etSignUpPhone.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignUpPhone.setSelection(etSignUpPhone.getText().length());
                    linePhoneNumber.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePhoneNumber.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etSignUpCode.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
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
                    .title(Config.Text.TIPS)
                    .content(Config.Text.HAS_ACCOUNT)
                    .left(Config.Text.CANCEL)
                    .right(Config.Text.SIGN_IN_NOW)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(android.view.View view) {
                            signDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(android.view.View view) {
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
                    tvGetCode.setText(R.string.get_code);
                } else {
                    tvGetCode.setText(String.format(Config.Text.VERIFY_CODE_FORMAT, t));
                }
            }

            @Override
            public void onComplete() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText(R.string.get_code);
            }
        });
    }

    @Override
    protected void onDestroy() {
        dispose(mCountDisposable);
        dismiss(signDialog);
        super.onDestroy();
    }

    @Override
    public void onSignUpOk(ServerUserInfo bean) {
        MyApplication.getAppContext().savedUser(bean);
        UserInfoUtil.updateUserInfo(bean);
        dispose(mCountDisposable);
        startActivity(SetPasswordActivity.class);
    }

    @Override
    protected void onTitleLeftClick() {
        ActivityStackUtils.popActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        finish();
    }

}
