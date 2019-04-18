package cn.treebear.kwifimanager.activity.me;

import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.account.SignInActivity;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.base.BaseTextWatcher;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserMobileContract;
import cn.treebear.kwifimanager.mvp.server.presenter.ModifyUserMobilePresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.CountObserver;
import cn.treebear.kwifimanager.util.CountUtil;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.util.UserInfoUtil;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 */
public class ModifyMobileActivity extends BaseActivity<ModifyUserMobileContract.Presenter, String> implements ModifyUserMobileContract.View {

    @BindView(R2.id.et_new_mobile)
    EditText etNewMobile;
    @BindView(R2.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R2.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R2.id.tv_complete)
    TextView tvComplete;
    private Disposable mCountDisposable;

    @Override
    public int layoutId() {
        return R.layout.activity_modify_mobile;
    }

    @Override
    public ModifyUserMobileContract.Presenter getPresenter() {
        return new ModifyUserMobilePresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.modify_mobile_number);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_MODIFY_USER_MOBILE, this);
        etNewMobile.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                tvComplete.setEnabled(s.length() > 0 && etVerifyCode.getText().length() > 0);
                tvGetVerifyCode.setEnabled(s.length() >= Config.Numbers.MIN_PWD_LENGTH);
            }
        });
        etVerifyCode.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                tvComplete.setEnabled(etNewMobile.getText().length() > 0 && s.length() > 0);
            }
        });
    }

    @OnClick(R2.id.tv_get_verify_code)
    public void onTvGetVerifyCodeClicked() {
        if (etNewMobile.getText().toString().length() != 11) {
            ToastUtils.showShort(R.string.input_correct_mobile);
            return;
        }
        tvGetVerifyCode.setEnabled(false);
        mPresenter.getVerifyCode(etNewMobile.getText().toString());
    }

    @OnClick(R2.id.tv_complete)
    public void onTvCompleteClicked() {
        if (etNewMobile.getText().toString().length() != 11) {
            ToastUtils.showShort(R.string.input_correct_mobile);
            return;
        }
        mPresenter.modifyMobileByVerify(etNewMobile.getText().toString(), etVerifyCode.getText().toString());
    }

    private void countDown() {
        tvGetVerifyCode.setEnabled(false);
        CountUtil.numberDown(Config.Numbers.VERIFY_CODE_WAIT_TIME, new CountObserver() {

            @Override
            public void onSubscribe(Disposable d) {
                mCountDisposable = d;
            }

            @Override
            public void onNext(Long t) {
                if (tvGetVerifyCode == null) {
                    return;
                }
                if (t <= 0) {
                    tvGetVerifyCode.setText(R.string.get_code);
                } else {
                    tvGetVerifyCode.setText(String.format(Config.Text.VERIFY_CODE_FORMAT, t));
                }
            }

            @Override
            public void onComplete() {
                if (tvGetVerifyCode == null) {
                    return;
                }
                tvGetVerifyCode.setEnabled(true);
                tvGetVerifyCode.setText(R.string.get_code);
            }
        });
    }

    @Override
    public void onLoadData(String resultData) {
        countDown();
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        super.onLoadFail(response, resultMsg, resultCode);
        ToastUtils.showShort(R.string.verify_code_send_failed_retry);
    }

    @Override
    public void onMobileModifySuccess() {
        TLog.w("OkHttp - clearUserInfo");
        UserInfoUtil.clearUserInfo();
        ToastUtils.showShort(R.string.modify_success_reload);
        MyApplication.getAppContext().setNeedUpdateUserInfo(true);
        startActivity(SignInActivity.class);
        ActivityStackUtils.finishAll(Config.Tags.TAG_MODIFY_USER_MOBILE);
    }

    @Override
    public void onModifyFailed() {
        ToastUtils.showShort(R.string.modify_failed_retry);
    }

    @Override
    protected void onDestroy() {
        dispose(mCountDisposable);
        ActivityStackUtils.popActivity(Config.Tags.TAG_MODIFY_USER_MOBILE, this);
        super.onDestroy();
    }
}
