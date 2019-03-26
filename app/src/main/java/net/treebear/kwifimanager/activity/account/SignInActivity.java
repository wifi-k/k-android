package net.treebear.kwifimanager.activity.account;


import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.MainActivity;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.bean.SUserCover;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.mvp.server.contract.PwdSignInContract;
import net.treebear.kwifimanager.mvp.server.presenter.PwdSignInPresenter;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;
import net.treebear.kwifimanager.util.TLog;
import net.treebear.kwifimanager.util.UserInfoUtil;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    public int layoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public PwdSignInContract.Presenter getPresenter() {
        return new PwdSignInPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
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
                ivEditClear.setVisibility(Check.hasContent(s) ? android.view.View.VISIBLE : android.view.View.GONE);
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
        etSignInPhone.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignInPhone.setSelection(etSignInPhone.getText().length());
                    linePhone.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePhone.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
        etSignInVerify.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
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

    @Override
    public void onLoadData(ServerUserInfo resultData) {
        MyApplication.getAppContext().savedUser(resultData);
        UserInfoUtil.getUserInfo().setToken(resultData.getToken());
        mPresenter.getUserInfo();
    }

    @Override
    public void onLoadFail(BaseResponse data, String resultMsg, int resultCode) {
        tvSignNext.setEnabled(true);
        TLog.w(resultMsg);
        ToastUtils.showShort(resultMsg);
        hideLoading();
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
            MyApplication.getAppContext().savedUser(user);
            UserInfoUtil.updateUserInfo(user);
            hideLoading();
            ToastUtils.showShort(Config.Tips.SIGN_IN_SUCCESS);
            startActivity(MainActivity.class);
            ActivityStackUtils.finishAll(Config.Tags.TAG_SIGN_ACCOUNT);
            ActivityStackUtils.finishAll(Config.Tags.TAG_LAUNCH_ROOT);
        }
    }

}
