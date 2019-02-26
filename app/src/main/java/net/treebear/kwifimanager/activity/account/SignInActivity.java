package net.treebear.kwifimanager.activity.account;


import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;

public class SignInActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView() {
        setTitleBack("");
        listenFocus();
    }

    private void listenFocus() {

    }
}
