package net.treebear.kwifimanager.activity.account;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.mvp.server.contract.GetVerifyContract;
import net.treebear.kwifimanager.mvp.server.presenter.GetVerifyPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.CountObserver;
import net.treebear.kwifimanager.util.CountUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * <h2>忘记密码</h2>
 * <li>获取验证码界面(本页)</li>
 * <li>重设密码界面</li>
 */
public class ForgetPwdCodeActivity extends BaseActivity<GetVerifyContract.Presenter, String> implements GetVerifyContract.View {

    @BindView(R.id.et_verify)
    EditText etVerify;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.line_phone)
    TextView linePhone;
    @BindView(R.id.iv_edit_clear)
    ImageView ivEditClear;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_sign_next)
    TextView tvSignNext;
    @BindView(R.id.iv_verify_clear)
    ImageView ivVerifyClear;
    /**
     * 倒计时订阅器
     */
    private Disposable mCountDisposable;
    /**
     * 服务端下发的验证码，用做本地验证
     */
    private String mVerifyCode;

    @Override
    public int layoutId() {
        return R.layout.activity_get_verify;
    }

    @Override
    public GetVerifyContract.Presenter getPresenter() {
        return new GetVerifyPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FORGET_PASSWORD, this);
        setTitleBack(R.string.find_password);
        tvSignNext.setText(R.string.next_step);
        listenFocus();
        listenTextChange();
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        etPhone.setText(Config.Text.EMPTY);
    }

    @OnClick(R.id.iv_verify_clear)
    public void onViewClicked() {
        etVerify.setText(Config.Text.EMPTY);
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        if (etPhone.getText().length() == 11) {
            mPresenter.getGetVerifyCode(Config.RequestType.VERIFY_CODE_FORGET_PWD, etPhone.getText().toString());
            tvGetCode.setEnabled(false);
        }
    }

    @OnClick(R.id.tv_sign_next)
    public void onTvSignNextClicked() {
        if (!etVerify.getText().toString().equals(mVerifyCode)) {
            ToastUtils.showShort(R.string.verify_code_input_error);
            return;
        }
        tvSignNext.setEnabled(true);
        dispose(mCountDisposable);
        Bundle bundle = new Bundle();
        bundle.putString(Keys.MOBILE, etPhone.getText().toString());
        bundle.putString(Keys.VERIFY_CODE, etVerify.getText().toString());
        startActivity(ForgetPwdSetActivity.class, bundle);
    }

    @Override
    public void onLoadData(String resultData) {
        mVerifyCode = resultData;
        ToastUtils.showShort(Config.Tips.VERIFY_SMS_SEND);
        countDown();
    }

    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etPhone.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivEditClear.setVisibility(Check.hasContent(s) && etPhone.hasFocus() ? View.VISIBLE : View.GONE);
                if (s.length() == Config.Numbers.PHONE_LENGTH) {
                    tvGetCode.setTextColor(Config.Colors.MAIN);
                    updateConfirmBtnEnable();
                } else {
                    tvGetCode.setTextColor(Config.Colors.TEXT_9B);
                }
            }
        });
        etVerify.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivVerifyClear.setVisibility(Check.hasContent(s) && etVerify.hasFocus() ? View.VISIBLE : View.GONE);
                updateConfirmBtnEnable();
            }
        });
    }

    /**
     * 更新确定按键可点击状态
     */
    private void updateConfirmBtnEnable() {
        tvSignNext.setEnabled(etPhone.getText().length() == Config.Numbers.PHONE_LENGTH &&
                etVerify.getText().length() == Config.Numbers.VERIFY_CODE_LENGTH);
    }

    /**
     * 配置EditText焦点变化监听
     */
    private void listenFocus() {
        etPhone.setOnFocusChangeListener((v, hasFocus) -> {
            ivEditClear.setVisibility(Check.hasContent(etPhone) && hasFocus ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etPhone.setSelection(etPhone.getText().length());
                linePhone.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePhone.setBackgroundColor(Config.Colors.LINE);
            }
        });
        etVerify.setOnFocusChangeListener((v, hasFocus) -> {
            ivVerifyClear.setVisibility(Check.hasContent(etVerify) && hasFocus ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etVerify.setSelection(etVerify.getText().length());
                linePassword.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePassword.setBackgroundColor(Config.Colors.LINE);
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
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FORGET_PASSWORD, this);
    }

}
