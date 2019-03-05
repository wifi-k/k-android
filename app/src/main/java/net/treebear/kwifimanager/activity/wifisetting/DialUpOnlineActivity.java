package net.treebear.kwifimanager.activity.wifisetting;

import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.util.ActivityStackUtils;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class DialUpOnlineActivity extends BaseActivity {

    @BindView(R.id.et_net_account)
    EditText etNetAccount;
    @BindView(R.id.line_account)
    TextView lineAccount;
    @BindView(R.id.et_net_passowrd)
    EditText etNetPassowrd;
    @BindView(R.id.iv_password_ryr)
    ImageView ivPasswordRyr;
    @BindView(R.id.line_password)
    TextView linePassword;
    @BindView(R.id.btn_dial_up_confirm)
    Button btnDialUpConfirm;
    private boolean passwordVisible = false;

    @Override
    public int layoutId() {
        return R.layout.activity_dial_up_online;
    }

    @Override
    protected void initView() {
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        listenFocus();
        listenInput();
    }

    private void listenInput() {
        BaseTextWatcher textWatcher = new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                btnDialUpConfirm.setEnabled(Check.hasContent(etNetAccount) && Check.hasContent(etNetPassowrd));
            }
        };
        etNetAccount.addTextChangedListener(textWatcher);
        etNetPassowrd.addTextChangedListener(textWatcher);
    }

    private void listenFocus() {
        etNetAccount.setOnFocusChangeListener((v, hasFocus) -> lineAccount.setBackgroundColor(hasFocus ? Config.Colors.MAIN : Config.Colors.LINE));
        etNetPassowrd.setOnFocusChangeListener((v, hasFocus) -> linePassword.setBackgroundColor(hasFocus ? Config.Colors.MAIN : Config.Colors.LINE));
    }

    @OnClick(R.id.iv_password_ryr)
    public void onIvPasswordRyrClicked() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            ivPasswordRyr.setImageResource(R.mipmap.ic_edit_eye_open_gray);
            //显示明文--设置为可见的密码
            etNetPassowrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivPasswordRyr.setImageResource(R.mipmap.ic_edit_eye_close_gray);
//            //显示密码--设置文本
            etNetPassowrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etNetPassowrd.setSelection(etNetPassowrd.getText().length());
    }

    @OnClick(R.id.btn_dial_up_confirm)
    public void onBtnDialUpConfirmClicked() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}