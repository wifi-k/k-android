package net.treebear.kwifimanager.activity.account;


import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.MainActivity;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.bean.UserInfoBean;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.contract.PwdSignInContract;
import net.treebear.kwifimanager.mvp.presenter.PwdSignInPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity<PwdSignInContract.IPwdSignInPresenter, UserInfoBean> implements PwdSignInContract.IPwdSignInView {

    @BindView(R.id.et_sign_in_verify)
    EditText etSignInVerify;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.et_sign_in_phone)
    EditText etSignInPhone;
    @BindView(R.id.line_phone)
    TextView linePhone;
    @BindView(R.id.tv_sign_next)
    TextView tvSignNext;
    @BindView(R.id.iv_edit_clear)
    ImageView ivEditClear;

    @Override
    public int layoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public PwdSignInContract.IPwdSignInPresenter getPresenter() {
        return new PwdSignInPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_SIGN_ACCOUNT,this);
        setTitleBack(R.string.sign_in_k);
        tvSignNext.setText(R.string.login);
        listenFocus();
        listenTextChange();
    }

    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etSignInPhone.addTextChangedListener(new BaseTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                ivEditClear.setVisibility(Check.hasContent(s) ? View.VISIBLE : View.GONE);
                if (s.length() == Config.Numbers.PHONE_LENGTH) {
                    updateConfirmBtnEnable();
                }
            }
        });
        etSignInVerify.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateConfirmBtnEnable();
            }
        });
    }

    private void updateConfirmBtnEnable() {
        tvSignNext.setEnabled(etSignInPhone.getText().length() == Config.Numbers.PHONE_LENGTH
                &&
                etSignInVerify.getText().length() >= Config.Numbers.MIN_PWD_LENGTH
        );
    }

    /**
     * 配置EditText焦点变化监听
     */
    private void listenFocus() {
        etSignInPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignInPhone.setSelection(etSignInPhone.getText().length());
                    linePhone.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePhone.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etSignInVerify.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignInVerify.setSelection(etSignInVerify.getText().length());
                    linePassword.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePassword.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
    }

    @OnClick(R.id.tv_forget_password)
    public void onTvForgetPasswordClicked() {
        startActivity(ForgetPwdCodeActivity.class);
    }

    @OnClick(R.id.tv_sign_next)
    public void onTvSignNextClicked() {
        mPresenter.signInByPwd(etSignInPhone.getText().toString(),etSignInVerify.getText().toString());
        tvSignNext.setEnabled(false);
    }

    @OnClick(R.id.tv_sms_sign_in)
    public void onTvSmsSignInClicked() {
        startActivity(VerifySignInActivity.class);
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvClearClick(){
        etSignInPhone.setText("");
    }

    @Override
    public void onLoadData(UserInfoBean resultData) {
        MyApplication.getAppContext().savedUser(resultData);
        ToastUtils.showShort(Config.Tips.SIGN_IN_SUCCESS);
        startActivity(MainActivity.class);
        ActivityStackUtils.finishAll(Config.Tags.TAG_SIGN_ACCOUNT);
    }

    @Override
    public void onLoadFail(String resultMsg, int resultCode) {
        tvSignNext.setEnabled(true);
        ToastUtils.showShort(resultMsg);
    }

    @Override
    protected void onTitleLeftClick() {
        ActivityStackUtils.popActivity(Config.Tags.TAG_SIGN_ACCOUNT,this);
        finish();
    }
}
