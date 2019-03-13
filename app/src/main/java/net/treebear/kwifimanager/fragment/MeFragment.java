package net.treebear.kwifimanager.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    Unbinder unbinder;
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

    @OnClick(R.id.tv_user_name)
    public void onTvUserNameClicked() {
    }

    @OnClick(R.id.tv_user_mobile)
    public void onTvUserMobileClicked() {
    }

    @OnClick(R.id.iv_me_user_header)
    public void onIvMeUserHeaderClicked() {
    }

    @OnClick(R.id.tv_my_k)
    public void onTvMyKClicked() {
    }

    @OnClick(R.id.tv_my_kid)
    public void onTvMyKidClicked() {
    }
}
