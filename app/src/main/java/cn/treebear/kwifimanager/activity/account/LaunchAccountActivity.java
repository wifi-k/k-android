package cn.treebear.kwifimanager.activity.account;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.MainActivity;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.model.GetUserInfoModel;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.UserInfoUtil;

/**
 * @author Tinlone
 * <h2>启动登录页</h2>
 * 可加入渐显动画
 * Let life be beautiful like summer flowers and death like autumn leaves.
 */
public class LaunchAccountActivity extends BaseActivity {

    @BindView(R.id.btn_signup)
    TextView btnSignup;
    @BindView(R.id.btn_signin)
    TextView btnSignin;

    @Override
    public int layoutId() {
        return R.layout.activity_launch_account;
    }

    @Override
    protected boolean inAll() {
        return false;
    }

    @Override
    protected void initView() {
        statusTransparentFontWhite();
        ActivityStackUtils.pressActivity(Config.Tags.TAG_LAUNCH_ROOT, this);
        if (Check.hasContent(UserInfoUtil.getUserInfo().getToken())) {
            btnSignin.setVisibility(View.GONE);
            btnSignup.setVisibility(View.GONE);
            MyApplication.getAppContext().savedUser(UserInfoUtil.getUserInfo());
            getUserInfo();
        }
    }

    private void getUserInfo() {
        showLoading("自动登录中...");
        new GetUserInfoModel().getUserInfo(new IModel.AsyncCallBack<BaseResponse<SUserCover>>() {
            @Override
            public void onSuccess(BaseResponse<SUserCover> resultData) {
                hideLoading();
                String token = MyApplication.getAppContext().getUser().getToken();
                ServerUserInfo user;
                if (resultData != null) {
                    user = resultData.getData().getUser();
                    user.setNodeSize(resultData.getData().getNodeSize());
                    user.setToken(token);
                    MyApplication.getAppContext().savedUser(user);
                    startActivity(MainActivity.class);
                    finish();
                }

            }

            @Override
            public void onFailed(BaseResponse data, String resultMsg, int resultCode) {
                if (btnSignin != null) {
                    btnSignin.setVisibility(View.VISIBLE);
                    btnSignup.setVisibility(View.VISIBLE);
                }
                ToastUtils.showShort(R.string.token_overdue_retry);
                hideLoading();
            }
        });
    }

    @OnClick(R.id.btn_signin)
    public void onSignInClick() {
        startActivity(SignInActivity.class);
    }

    @OnClick(R.id.btn_signup)
    public void onSignUpClick() {
        startActivity(SignUpActivity.class);
    }

    @Override
    protected void onDestroy() {
        ActivityStackUtils.popActivity(Config.Tags.TAG_LAUNCH_ROOT, this);
        super.onDestroy();
    }

}
