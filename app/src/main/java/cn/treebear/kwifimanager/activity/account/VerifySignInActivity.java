package cn.treebear.kwifimanager.activity.account;

import android.text.Editable;
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
import cn.treebear.kwifimanager.mvp.server.contract.CodeSignInContract;
import cn.treebear.kwifimanager.mvp.server.presenter.CodeSignInPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.CountObserver;
import cn.treebear.kwifimanager.util.CountUtil;
import cn.treebear.kwifimanager.util.UserInfoUtil;
import io.reactivex.disposables.Disposable;

/**
 * <h2>验证码登录界面</h2>
 * 登录和验证系列界面尚有很大优化空间
 */
public class VerifySignInActivity extends BaseActivity<CodeSignInContract.Presenter, String> implements CodeSignInContract.View {


    @BindView(R.id.et_verify)
    EditText etSignInVerify;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.et_phone)
    EditText etSignInPhone;
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
     * 系统下发验证码，用于本地验证
     */
    private String mVerifyCode = "";
    /**
     * 计时器订阅器
     */
    private Disposable mCountDisposable;

    @Override
    public int layoutId() {
        return R.layout.activity_get_verify;
    }

    @Override
    public CodeSignInContract.Presenter getPresenter() {
        return new CodeSignInPresenter();
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        setTitleBack(R.string.sign_in_k);
        tvSignNext.setText(R.string.sign_in_k);
        listenFocus();
        listenTextChange();
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        etSignInPhone.setText("");
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        if (etSignInPhone.getText().length() == 11) {
            mPresenter.getSignInVerifyCode(etSignInPhone.getText().toString());
            tvGetCode.setEnabled(false);
        }
    }

    @OnClick(R.id.iv_verify_clear)
    public void onViewClicked() {
        etSignInVerify.setText("");
    }

    @Override
    public void onLoadData(String resultData) {
        mVerifyCode = resultData;
        ToastUtils.showShort(Config.Tips.VERIFY_SMS_SEND);
        countDown();
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        ToastUtils.showShort(resultMsg);
        hideLoading();
    }

    @OnClick(R.id.tv_sign_next)
    public void onTvSignNextClicked() {
        if (!etSignInVerify.getText().toString().equals(mVerifyCode)) {
            ToastUtils.showShort(Config.Tips.VERIFY_CODE_ERROR);
            return;
        }
        showLoading();
        mPresenter.signInByVerifyCode(etSignInPhone.getText().toString(),
                etSignInVerify.getText().toString());
    }

    /**
     * 配置EditText文本变化监听
     */
    private void listenTextChange() {
        etSignInPhone.addTextChangedListener(new BaseTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                ivEditClear.setVisibility(Check.hasContent(s) && etSignInPhone.hasFocus() ? View.VISIBLE : View.GONE);
                if (s.length() == Config.Numbers.PHONE_LENGTH) {
                    tvGetCode.setTextColor(Config.Colors.MAIN);
                    updateConfirmBtnEnable();
                } else {
                    tvGetCode.setTextColor(Config.Colors.TEXT_9B);
                }
            }
        });
        etSignInVerify.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivVerifyClear.setVisibility(Check.hasContent(s) && etSignInVerify.hasFocus() ? View.VISIBLE : View.GONE);
                updateConfirmBtnEnable();
            }
        });
    }

    /**
     * 更新确认按键可点击状态
     */
    private void updateConfirmBtnEnable() {
        tvSignNext.setEnabled(etSignInPhone.getText().length() == Config.Numbers.PHONE_LENGTH &&
                etSignInVerify.getText().length() == Config.Numbers.VERIFY_CODE_LENGTH);
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
            ivVerifyClear.setVisibility(Check.hasContent(etSignInVerify) && hasFocus ? View.VISIBLE : View.GONE);
            if (hasFocus) {
                etSignInVerify.setSelection(etSignInVerify.getText().length());
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
    public void onSignInOk(ServerUserInfo bean) {
        MyApplication.getAppContext().savedUser(bean);
        mPresenter.getUserInfo();
    }

    @Override
    public void onUserInfoLoaded(SUserCover bean) {
        if (bean != null) {
            ServerUserInfo user = bean.getUser();
            user.setToken(MyApplication.getAppContext().getUser().getToken());
            user.setNodeSize(bean.getNodeSize());
            MyApplication.getAppContext().savedUser(user);
            UserInfoUtil.updateUserInfo(user);
            dispose(mCountDisposable);
            hideLoading();
            ToastUtils.showShort(Config.Tips.SIGN_IN_SUCCESS);
            startActivity(MainActivity.class);
            ActivityStackUtils.finishAll(Config.Tags.TAG_SIGN_ACCOUNT);
            ActivityStackUtils.finishAll(Config.Tags.TAG_LAUNCH_ROOT);
        }
    }

    @Override
    protected void onTitleLeftClick() {
        hideLoading();
        ActivityStackUtils.popActivity(Config.Tags.TAG_SIGN_ACCOUNT, this);
        finish();
    }

}