package cn.treebear.kwifimanager.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.home.myk.MyDeviceListActivity;
import cn.treebear.kwifimanager.activity.me.MyChildrenListActivity;
import cn.treebear.kwifimanager.activity.me.SettingsActivity;
import cn.treebear.kwifimanager.activity.me.UserInfoActivity;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;
import cn.treebear.kwifimanager.mvp.server.presenter.GetUserInfoPresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.UserInfoUtil;

/**
 * @author Administrator
 */
public class MeFragment extends BaseFragment<GetUserInfoContract.Presenter, SUserCover> implements GetUserInfoContract.View {
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
    public GetUserInfoContract.Presenter getPresenter() {
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
    public void onLoadData(SUserCover resultData) {
        userInfo = resultData.getUser();
        userInfo.setNodeSize(resultData.getNodeSize());
        MyApplication.getAppContext().savedUser(userInfo);
        UserInfoUtil.updateUserInfo(userInfo);
        updateUserInfo();
        MyApplication.getAppContext().setNeedUpdateUserInfo(false);
    }

    @OnClick(R.id.iv_settings)
    public void onIvSettingsClicked() {
        startActivity(SettingsActivity.class);
    }

    @OnClick({R.id.tv_user_name, R.id.tv_user_mobile, R.id.iv_me_user_header})
    public void onTvUserNameClicked(android.view.View v) {
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
