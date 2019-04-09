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
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseTextWatcher;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.mvp.server.contract.PwdSignInContract;
import cn.treebear.kwifimanager.mvp.server.presenter.PwdSignInPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.util.UserInfoUtil;
import cn.treebear.kwifimanager.widget.dialog.TMessageDialog;

/**
 * <h2>密码登录界面</h2>
 */
public class SignInActivity extends BaseActivity<PwdSignInContract.Presenter, ServerUserInfo> implements PwdSignInContract.View {

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
    @BindView(R.id.iv_password_eye)
    ImageView ivPasswordEye;
    @BindView(R.id.iv_password_clear)
    ImageView ivPasswordClear;
    private boolean passwordVisible = false;
    private TMessageDialog noSignDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected boolean inAll() {
        return false;
    }

    @Override
    public PwdSignInContract.Presenter getPresenter() {
        return new PwdSignInPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        setTitleBack(Config.Text.EMPTY);
        tvSignNext.setText(R.string.login);
        listenFocus();
        listenTextChange();
    }

    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etSignInPhone.requestFocus();
        etSignInPhone.addTextChangedListener(new BaseTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                ivEditClear.setVisibility(Check.hasContent(s) && etSignInPhone.hasFocus() ? View.VISIBLE : View.GONE);
                if (s.length() == Config.Numbers.PHONE_LENGTH) {
                    updateConfirmBtnEnable();
                }
            }
        });
        etSignInVerify.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivPasswordClear.setVisibility(Check.hasContent(s) && etSignInVerify.hasFocus() ? View.VISIBLE : View.GONE);
                ivPasswordEye.setVisibility(Check.hasContent(s) && etSignInVerify.hasFocus() ? View.VISIBLE : View.GONE);
                updateConfirmBtnEnable();
            }
        });
    }

    /**
     * 更新确认按键可点击状态
     */
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
        etSignInPhone.setOnFocusChangeListener((v, hasFocus) -> {
            ivEditClear.setVisibility(Check.hasContent(etSignInPhone) && hasFocus ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etSignInPhone.setSelection(etSignInPhone.getText().length());
                linePhone.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePhone.setBackgroundColor(Config.Colors.LINE);
            }
        });
        etSignInVerify.setOnFocusChangeListener((v, hasFocus) -> {
            ivPasswordClear.setVisibility(Check.hasContent(etSignInVerify) && hasFocus ? View.VISIBLE : View.GONE);
            ivPasswordEye.setVisibility(Check.hasContent(etSignInVerify) && hasFocus ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etSignInVerify.setSelection(etSignInVerify.getText().length());
                linePassword.setBackgroundColor(Config.Colors.MAIN);
            } else {
                linePassword.setBackgroundColor(Config.Colors.LINE);
            }
        });
    }

    @OnClick(R.id.tv_forget_password)
    public void onTvForgetPasswordClicked() {
        startActivity(ForgetPwdCodeActivity.class);
    }

    @OnClick(R.id.tv_sign_next)
    public void onTvSignNextClicked() {
        showLoading();
        mPresenter.signInByPwd(etSignInPhone.getText().toString(), etSignInVerify.getText().toString());
        tvSignNext.setEnabled(false);
    }

    @OnClick(R.id.tv_sms_sign_in)
    public void onTvSmsSignInClicked() {
        startActivity(VerifySignInActivity.class);
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvClearClick() {
        etSignInPhone.setText("");
    }

    @OnClick(R.id.iv_password_eye)
    public void onIvPasswordEyeClicked() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            ivPasswordEye.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            etSignInVerify.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivPasswordEye.setImageResource(R.mipmap.ic_edit_eye_close_gray);
            etSignInVerify.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etSignInVerify.setSelection(etSignInVerify.getText().length());
    }

    @OnClick(R.id.iv_password_clear)
    public void onIvPasswordClearClicked() {
        etSignInVerify.setText("");
    }

    @Override
    public void onLoadData(ServerUserInfo resultData) {
        MyApplication.getAppContext().savedUser(resultData);
        UserInfoUtil.getUserInfo().setToken(resultData.getToken());
        mPresenter.getUserInfo();
    }

    @Override
    public void onLoadFail(BaseResponse data, String resultMsg, int resultCode) {
        tvSignNext.setEnabled(true);
        hideLoading();
        switch (resultCode) {
            case ApiCode.DB_NOT_FOUND_RECORD:
                showNoSignDialog();
                break;
            default:
                ToastUtils.showShort(R.string.message_error_check_retry);
                break;
        }
    }

    private void showNoSignDialog() {
        if (noSignDialog == null) {
            noSignDialog = new TMessageDialog(this).withoutMid()
                    .title(R.string.tips)
                    .content(R.string.mobile_not_sign_up)
                    .left(R.string.cancel)
                    .right(R.string.sign_up_now)
                    .doClick(new TMessageDialog.DoClickListener() {
                        @Override
                        public void onClickLeft(View view) {
                            noSignDialog.dismiss();
                        }

                        @Override
                        public void onClickRight(View view) {
                            noSignDialog.dismiss();
                            startActivity(SignUpActivity.class);
                            finish();
                        }
                    });
        }
        noSignDialog.show();
    }

    @Override
    protected void onTitleLeftClick() {
        ActivityStackUtils.popActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        finish();
    }

    @Override
    public void onnUserInfoLoaded(SUserCover bean) {
        if (bean != null) {
            ServerUserInfo user = bean.getUser();
            user.setToken(MyApplication.getAppContext().getUser().getToken());
            user.setNodeSize(bean.getNodeSize());
            TLog.i(user);
            MyApplication.getAppContext().savedUser(user);
            hideLoading();
            ToastUtils.showShort(Config.Tips.SIGN_IN_SUCCESS);
            startActivity(MainActivity.class);
            ActivityStackUtils.finishAll(Config.Tags.TAG_SIGN_ACCOUNT);
            ActivityStackUtils.finishAll(Config.Tags.TAG_LAUNCH_ROOT);
        }
    }

}
