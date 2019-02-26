package net.treebear.kwifimanager.activity.account;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.et_sign_up_verify)
    EditText etSignUpPassword;
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

    @Override
    public int layoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initView() {
        statusWhiteFontBlack();
        setTitleBack("");
        listenFocus();
        listenTextChange();
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
                if (s.length()==11){
                    // TODO: 2019/2/26 检查手机号合法性
                    tvGetCode.setTextColor(Config.Colors.MAIN);
                }
            }
        });
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
        etSignUpPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSignUpPassword.setSelection(etSignUpPassword.getText().length());
                    linePassword.setBackgroundColor(Config.Colors.MAIN);
                } else {
                    linePassword.setBackgroundColor(Config.Colors.LINE);
                }
            }
        });
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        etSignUpPhone.setText("");
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        if (etSignUpPhone.getText().length() == 11){

        }
    }

    @OnClick(R.id.tv_sign_next)
    public void onTvSignNextClicked() {
    }
}
