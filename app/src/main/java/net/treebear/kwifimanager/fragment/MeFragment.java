package net.treebear.kwifimanager.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.home.myk.MyDeviceListActivity;
import net.treebear.kwifimanager.activity.me.MyChildrenListActivity;
import net.treebear.kwifimanager.activity.me.SettingsActivity;
import net.treebear.kwifimanager.activity.me.UserInfoActivity;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.bean.ServerUserInfo;
import net.treebear.kwifimanager.config.GlideApp;
import net.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;
import net.treebear.kwifimanager.mvp.server.presenter.GetUserInfoPresenter;
import net.treebear.kwifimanager.util.Check;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class MeFragment extends BaseFragment<GetUserInfoContract.IGetUserInfoPresenter, ServerUserInfo> implements GetUserInfoContract.IGetUserInfoView {
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
    public GetUserInfoContract.IGetUserInfoPresenter getPresenter() {
        return new GetUserInfoPresenter();
    }

    @Override
    protected void initView() {
        updateUserInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && MyApplication.getAppContext().isNeedUpdateUserInfo()) {
            mPresenter.getUserInfo();
        }
    }

    private void updateUserInfo() {
        userInfo = MyApplication.getAppContext().getUser();
        tvUserName.setText(Check.hasContent(userInfo.getName()) ? userInfo.getName() : "用户" + userInfo.getMobile());
        tvUserMobile.setText(userInfo.getMobile());
        GlideApp.with(mRootView)
                .load(userInfo.getAvatar())
                .placeholder(R.mipmap.ic_me_header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_me_header)
                .circleCrop()
                .into(ivMeUserHeader);
    }

    @Override
    public void onLoadData(ServerUserInfo resultData) {
        userInfo = resultData;
        MyApplication.getAppContext().savedUser(resultData);
        updateUserInfo();
        MyApplication.getAppContext().setNeedUpdateUserInfo(false);
    }

    @OnClick(R.id.iv_settings)
    public void onIvSettingsClicked() {
        startActivity(SettingsActivity.class);
    }

    @OnClick({R.id.tv_user_name, R.id.tv_user_mobile, R.id.iv_me_user_header})
    public void onTvUserNameClicked(View v) {
        startActivity(UserInfoActivity.class);
    }

    @OnClick(R.id.tv_my_k)
    public void onTvMyKClicked() {
        startActivity(MyDeviceListActivity.class);
    }

    @OnClick(R.id.tv_my_kid)
    public void onTvMyKidClicked() {
        startActivity(MyChildrenListActivity.class);
    }
}
