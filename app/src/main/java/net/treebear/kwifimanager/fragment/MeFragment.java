package net.treebear.kwifimanager.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.me.SettingsActivity;
import net.treebear.kwifimanager.activity.me.UserInfoActivity;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.iv_me_user_header)
    ImageView ivMeUserHeader;
    private ServerUserInfo userInfo;

    @Override
    public int layoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        updateUserInfo();
    }

    private void updateUserInfo() {
        userInfo = MyApplication.getAppContext().getUser();
        tvUserName.setText(Check.hasContent(userInfo.getName()) ? userInfo.getName() : "用户" + userInfo.getMobile());
        tvUserMobile.setText(userInfo.getMobile());
    }

    @OnClick(R.id.iv_settings)
    public void onIvSettingsClicked() {
        startActivity(SettingsActivity.class);
    }

    @OnClick({R.id.tv_user_name,R.id.tv_user_mobile,R.id.iv_me_user_header})
    public void onTvUserNameClicked(View v) {
        startActivity(UserInfoActivity.class);
    }

    @OnClick(R.id.tv_my_k)
    public void onTvMyKClicked() {
    }

    @OnClick(R.id.tv_my_kid)
    public void onTvMyKidClicked() {
    }
}
