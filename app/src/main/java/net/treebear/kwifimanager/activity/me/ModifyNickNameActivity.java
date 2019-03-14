package net.treebear.kwifimanager.activity.me;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.base.BaseTextWatcher;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class ModifyNickNameActivity extends BaseActivity {

    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.iv_edit_close)
    ImageView ivEditClose;

    @Override
    public int layoutId() {
        return R.layout.activity_modify_nick_name;
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
                ivEditClose.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @OnClick(R.id.iv_edit_close)
    public void onViewClicked() {
        etNickName.setText("");
    }

}
