package cn.treebear.kwifimanager.activity.account;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseTextWatcher;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.mvp.server.contract.GetVerifyContract;
import cn.treebear.kwifimanager.mvp.server.presenter.GetVerifyPresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.CountObserver;
import cn.treebear.kwifimanager.util.CountUtil;
import io.reactivex.disposables.Disposable;

/**
 * 预备使用一个界面解决所有获取验证码业务
 * 考虑到会使界面臃肿，后期难以维护，故弃用
 */
@Deprecated
public class GetVerifyActivity extends BaseActivity<GetVerifyContract.Presenter, String> implements GetVerifyContract.View {
    @BindView(R2.id.et_verify)
    EditText etSignUpVerify;
    @BindView(R2.id.line_password)
    TextView linePassword;
    @BindView(R2.id.et_phone)
    EditText etSignUpPhone;
    @BindView(R2.id.line_phone)
    TextView linePhone;
    @BindView(R2.id.iv_edit_clear)
    ImageView ivEditClear;
    @BindView(R2.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R2.id.tv_sign_next)
    TextView tvSignNext;
    /**
     * 界面title
     */
    private String title;
    /**
     * 目标界面代码
     */
    private int type;
    /**
     * 确认按键文字
     */
    private String confirmText;
    /**
     * 服务端下发验证码，用作本地校验
     */
    private String mVerifyCode;
    private Disposable mCountDisposable;

    @Override
    public int layoutId() {
        return R.layout.activity_get_verify;
    }

    @Override
    public GetVerifyContract.Presenter getPresenter() {
        return new GetVerifyPresenter();
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            title = params.getString(Keys.TITLE, Config.Text.EMPTY);
            type = params.getInt(Keys.TYPE, -1);
            confirmText = params.getString(Keys.CONFIRM_BUTTON_TEXT, Config.Text.SIGN_IN);
        }
    }

    @Override
    protected void initView() {
        setTitleBack(title);
        tvSignNext.setText(confirmText);
        listenFocus();
        listenTextChange();
    }

    @Override
    public void onLoadData(String resultData) {
        mVerifyCode = resultData;
        ToastUtils.showShort(Config.Tips.VERIFY_SMS_SEND);
        countDown();
    }

    @OnClick(R2.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        etSignUpPhone.setText("");
    }

    @OnClick(R2.id.tv_get_code)
    public void onTvGetCodeClicked() {
        if (etSignUpPhone.getText().length() == 11) {
            mPresenter.getGetVerifyCode(type, etSignUpPhone.getText().toString());
            tvGetCode.setEnabled(false);
        }
    }

    @OnClick(R2.id.tv_sign_next)
    public void onTvSignNextClicked() {
        if (!etSignUpVerify.getText().toString().equals(mVerifyCode)) {
            ToastUtils.showShort(Config.Tips.VERIFY_CODE_ERROR);
            return;
        }
        dispose(mCountDisposable);
        // 考虑将所有获取验证码的功能放到一个类中，考虑到可能过于臃肿，暂不做此项设计
        switch (type) {
            case Config.RequestType.VERIFY_CODE_SIGN_UP:
                break;
            case Config.RequestType.VERIFY_CODE_FORGET_PWD:
                break;
            case Config.RequestType.VERIFY_CODE_MODIFY_PHONE:
                break;
            case Config.RequestType.VERIFY_CODE_SIGN_IN:
                break;
            default:
                break;
        }
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
        etSignUpVerify.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateConfirmBtnEnable();
            }
        });
    }

    private void updateConfirmBtnEnable() {
        tvSignNext.setEnabled(etSignUpPhone.getText().length() == Config.Numbers.PHONE_LENGTH &&
                etSignUpVerify.getText().length() == Config.Numbers.VERIFY_CODE_LENGTH);
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
                    linePhone.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePhone.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etSignUpVerify.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignUpVerify.setSelection(etSignUpVerify.getText().length());
                    linePassword.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePassword.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
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
        super.onDestroy();
    }
}
