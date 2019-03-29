package cn.treebear.kwifimanager.activity.me;

import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseTextWatcher;
import cn.treebear.kwifimanager.bean.QiNiuUserBean;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserInfoContract;
import cn.treebear.kwifimanager.mvp.server.presenter.ModifyUserInfoPresenter;
import cn.treebear.kwifimanager.util.Check;

/**
 * @author Administrator
 */
public class ModifyNickNameActivity extends BaseActivity<ModifyUserInfoContract.Presenter, SUserCover> implements ModifyUserInfoContract.View {

    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.iv_edit_close)
    ImageView ivEditClose;

    @Override
    public int layoutId() {
        return R.layout.activity_modify_nick_name;
    }

    @Override
    public ModifyUserInfoContract.Presenter getPresenter() {
        return new ModifyUserInfoPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(getString(R.string.nick_name), getString(R.string.complete));
        ServerUserInfo userInfo = MyApplication.getAppContext().getUser();
        String nickName = Check.hasContent(userInfo.getName()) ? userInfo.getName().trim() : "用户" + userInfo.getMobile();
        etNickName.setText(nickName);
        etNickName.setSelection(nickName.length());
        etNickName.requestFocus();
        etNickName.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivEditClose.setVisibility(s.length() > 0 ? android.view.View.VISIBLE : android.view.View.GONE);
            }
        });
    }

    @OnClick(R.id.iv_edit_close)
    public void onViewClicked() {
        etNickName.setText("");
    }

    @Override
    protected void onTitleRightClick() {
        mPresenter.modifyUserInfo(etNickName.getText().toString(), null);
    }

    @Override
    public void onQiNiuTokenResponse(QiNiuUserBean result) {
//        此处不需要
    }

    @Override
    public void onModifyUserInfo() {
        onTitleLeftClick();
    }
}
