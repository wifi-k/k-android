package cn.treebear.kwifimanager.fragment;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.home.myk.MyDeviceListActivity;
import cn.treebear.kwifimanager.activity.me.MyChildrenListActivity;
import cn.treebear.kwifimanager.activity.me.SettingsActivity;
import cn.treebear.kwifimanager.activity.me.UserInfoActivity;
import cn.treebear.kwifimanager.base.BaseFragment;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.mvp.server.contract.GetUserInfoContract;
import cn.treebear.kwifimanager.mvp.server.presenter.GetUserInfoPresenter;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.widget.dialog.TInputDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Administrator
 */
public class MeFragment extends BaseFragment<GetUserInfoContract.Presenter, SUserCover> implements GetUserInfoContract.View {
    @BindView(R2.id.tv_user_name)
    TextView tvUserName;
    @BindView(R2.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R2.id.iv_me_user_header)
    CircleImageView ivMeUserHeader;

    private ServerUserInfo userInfo;
    private TInputDialog inputDialog;

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
    public void onResume() {
        super.onResume();
        if (MyApplication.getAppContext().isNeedUpdateUserInfo()) {
            mPresenter.getUserInfo();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateUserInfo() {
        userInfo = MyApplication.getAppContext().getUser();
        if (userInfo == null) {
            return;
        }
        tvUserName.setText(Check.hasContent(userInfo.getName()) ? userInfo.getName() : "用户" + userInfo.getMobile().substring(userInfo.getMobile().length() - 4));
        if (Check.hasContent(userInfo.getName())) {
            tvUserName.setText(userInfo.getName());
        } else if (Check.maxThen(userInfo.getMobile(), 4)) {
            tvUserName.setText(getString(R.string.user) + (userInfo.getMobile().substring(userInfo.getMobile().length() - 4)));
        }
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
        userInfo.setToken(MyApplication.getAppContext().getUser().getToken());
        MyApplication.getAppContext().savedUser(userInfo);
        updateUserInfo();
        MyApplication.getAppContext().setNeedUpdateUserInfo(false);
    }

    @OnClick(R2.id.iv_settings)
    public void onIvSettingsClicked() {
        startActivity(SettingsActivity.class);
    }

    @OnClick({R.id.tv_user_name, R.id.tv_user_mobile, R.id.iv_me_user_header})
    public void onTvUserNameClicked(android.view.View v) {
        startActivity(UserInfoActivity.class);
    }

    @OnClick(R2.id.tv_my_k)
    public void onTvMyKClicked() {
        startActivity(MyDeviceListActivity.class);
    }

    @OnClick(R2.id.tv_my_kid)
    public void onTvMyKidClicked() {
        startActivity(MyChildrenListActivity.class);
    }

    @OnClick(R2.id.tv_into_new_family)
    public void onTvAddNewFamilyClicked() {
        showFamilyCodeDialog();
    }

    @Override
    public void onJoinFamilySuccess() {
        dismiss(inputDialog);
        ToastUtils.showShort(R.string.join_family_success);
    }

    @Override
    public void onJoinFamilyFailed(BaseResponse response) {
        ToastUtils.showShort(R.string.family_code_error);
    }

    private void showFamilyCodeDialog() {
        if (inputDialog == null) {
            inputDialog = new TInputDialog(mContext);
            inputDialog.setTitle(R.string.input_family_code_into_family);
            inputDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {

                @Override
                public void onLeftClick(String s) {
                    dismiss(inputDialog);
                }

                @Override
                public void onRightClick(String s) {
                    mPresenter.joinFamily(s.trim());
                }
            });
        }
        inputDialog.show();
    }

    @Override
    public void onDestroy() {
        dismiss(inputDialog);
        super.onDestroy();
    }
}
