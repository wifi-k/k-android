package net.treebear.kwifimanager.activity.me;

import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.util.CountObserver;
import net.treebear.kwifimanager.util.CountUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 */
public class ModifyMobileActivity extends BaseActivity {

    @BindView(R.id.et_new_mobile)
    EditText etNewMobile;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    private Disposable mCountDisposable;

    @Override
    public int layoutId() {
        return R.layout.activity_modify_mobile;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.modify_mobile_number);
        etNewMobile.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                tvComplete.setEnabled(s.length() > 0 && etVerifyCode.getText().length() > 0);
                tvGetVerifyCode.setEnabled(s.length()== Config.Numbers.PHONE_LENGTH);
            }
        });
        etVerifyCode.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                tvComplete.setEnabled(etNewMobile.getText().length() > 0 && s.length() > 0);
            }
        });
    }

    @OnClick(R.id.tv_get_verify_code)
    public void onTvGetVerifyCodeClicked() {
    }

    @OnClick(R.id.tv_complete)
    public void onTvCompleteClicked() {
    }

    private void countDown(){
        CountUtil.numberDown(Config.Numbers.VERIFY_CODE_WAIT_TIME, new CountObserver() {

            @Override
            public void onSubscribe(Disposable d) {
                mCountDisposable = d;
            }

            @Override
            public void onNext(Long t) {
                if (t <= 0) {
                    tvGetVerifyCode.setText(R.string.get_code);
                } else {
                    tvGetVerifyCode.setText(String.format(Config.Text.VERIFY_CODE_FORMAT, t));
                }
            }

            @Override
            public void onComplete() {
                tvGetVerifyCode.setEnabled(true);
                tvGetVerifyCode.setText(R.string.get_code);
            }
        });
    }
}
