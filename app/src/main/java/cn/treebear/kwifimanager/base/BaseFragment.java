package cn.treebear.kwifimanager.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.activity.WebsiteActivity;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.mvp.IView;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.dialog.LoadingProgressDialog;
import cn.treebear.kwifimanager.widget.dialog.TDialog;
import cn.treebear.kwifimanager.widget.pop.TPop;

/**
 * @author Tinlone
 * @date 2018/3/23.
 * The little flower lies in the dust. It sought the path of the butterfly.
 */

public abstract class BaseFragment<P extends IPresenter, DATA> extends Fragment implements IView<DATA> {
    public static final String FULL_SCREEN = "full_screen";
    public static final String AUTO_TITLE = "auto_title";
    public static final String RIGHT_TEXT = "right_text";
    public static final String TITLE = "title";
    public static final String URL = "url";

    protected P mPresenter;
    protected Context mContext;
    protected View mRootView;
    /**
     * 限制666ms内多次跳转同一界面
     */
    private long lastClick = 0L;
    private Unbinder unbinder;
    protected boolean hasLoadData = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(layoutId(), container, false);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        initData();
        TLog.w("构建" + getClass().getSimpleName());
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        hideLoading();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.dettachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        lastClick = 0L;
        super.onDestroy();
    }

    /**
     * 获取布局id
     *
     * @return id
     */
    public abstract int layoutId();

    /**
     * 获取presenter
     *
     * @return presenter
     */
    public P getPresenter() {
        return null;
    }

    /**
     * 提供初始化控件入口
     */
    protected void initView() {
    }

    /**
     * 提供初始化数据入口
     * （非必选）
     */
    protected void initData() {
    }

    /**
     * 设置title样式
     *
     * @param title title文字
     */
    protected void setTitle(String title) {
        setTitle(0, title, "", 0, true);
    }

    protected void setTitle(String title, boolean white) {
        setTitle(0, title, "", 0, white);
    }

    /**
     * 设置title样式
     *
     * @param title title文字
     */
    protected void setTitle(@StringRes int title) {
        setTitle(mContext.getResources().getString(title));
    }

    protected void setTitle(@StringRes int title, boolean white) {
        setTitle(mContext.getResources().getString(title), white);
    }

    /**
     * 设置title样式
     *
     * @param title title文字
     * @param right 右侧文字
     */
    protected void setTitleBack(String title, String right) {
        setTitle(R.mipmap.back, title, right, 0, true);
    }

    protected void setTitleBack(String title, String right, boolean white) {
        setTitle(R.mipmap.back, title, right, 0, white);
    }

    /**
     * 设置title样式
     *
     * @param leftRes 左侧资源
     * @param title   title文字
     */
    protected void setTitle(@DrawableRes int leftRes, String title) {
        setTitle(leftRes, title, "", 0, true);
    }

    /**
     * 设置title样式
     *
     * @param leftRes    左侧资源
     * @param title      title文字
     * @param rightTitle 右侧文字
     * @param rightRes   右侧资源
     */
    protected void setTitle(@DrawableRes int leftRes, String title, String rightTitle, @DrawableRes int rightRes, boolean white) {
        try {
            RelativeLayout statusBar = mRootView.findViewById(R.id.rl_title);
            ImageView ivLeft = mRootView.findViewById(R.id.iv_back);
            ImageView ivRight = mRootView.findViewById(R.id.iv_title_right);
            TextView tvTitle = mRootView.findViewById(R.id.tv_title_text);
            TextView tvRight = mRootView.findViewById(R.id.tv_title_right);
            if (leftRes != 0) {
                ivLeft.setImageResource(leftRes);
            }
            if (rightRes != 0) {
                ivRight.setImageResource(rightRes);
            }
            ivLeft.setVisibility(leftRes != 0 ? View.VISIBLE : View.GONE);
            ivRight.setVisibility(rightRes != 0 ? View.VISIBLE : View.GONE);
            tvTitle.setText(title);
            tvRight.setText(rightTitle);
            tvRight.setVisibility(Check.hasContent(rightTitle) ? View.VISIBLE : View.GONE);
            ivLeft.setOnClickListener(view -> onTitleLeftClick());
            if (Check.hasContent(rightTitle)) {
                tvRight.setOnClickListener(view -> onTitleRightClick());
            }
            if (white) {
                statusBar.setBackgroundColor(Color.WHITE);
                tvTitle.setTextColor(Config.Colors.TEXT_28);
                tvRight.setTextColor(Config.Colors.TEXT_28);
            } else {
                statusBar.setBackgroundColor(Color.TRANSPARENT);
                tvTitle.setTextColor(Color.WHITE);
                tvRight.setTextColor(Color.WHITE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 右侧点击事件
     */
    protected void onTitleRightClick() {

    }

    /**
     * title左图标点击，默认关闭界面，可重写
     */
    protected void onTitleLeftClick() {

    }


    /**
     * 打开新界面
     *
     * @param clazz 界面类
     *              DEBUG 修复笔误 BaseFragment -> BaseActivity
     */
    public void startActivity(Class<? extends BaseActivity> clazz) {
        startActivity(clazz, null);
    }

    /**
     * 打开新界面
     *
     * @param clazz  界面类
     * @param bundle 数据
     *               DEBUG 修复笔误 BaseFragment -> BaseActivity
     */
    public void startActivity(Class<? extends BaseActivity> clazz, Bundle bundle) {
        if (noDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(mContext, clazz);
            if (bundle == null) {
                bundle = new Bundle();
            }
            intent.putExtras(bundle);
            lastClick = System.currentTimeMillis();
            startActivity(intent);
        }
    }

    public void openWebsite(String url) {
        openWebsite(url, "", true, "", false);
    }

    public void openWebsite(String url, String title) {
        openWebsite(url, title, true, "", false);
    }

    public void openWebsite(String url, boolean fullScreen) {
        openWebsite(url, "", true, "", fullScreen);
    }

    public void openWebsite(String url, String title, boolean fullScreen) {
        openWebsite(url, title, false, "", fullScreen);
    }

    public void openWebsite(String url, boolean autoTitle, String rightText, boolean fullScreen) {
        openWebsite(url, "", autoTitle, rightText, fullScreen);
    }

    /**
     * 打开加载H5页面
     *
     * @param url        URL
     * @param title      TITLE
     * @param autoTitle  自动获取title
     * @param rightText  右侧标题字符
     * @param fullScreen 全屏？
     */
    public void openWebsite(String url, String title, boolean autoTitle, String rightText, boolean fullScreen) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        bundle.putString(RIGHT_TEXT, rightText);
        bundle.putBoolean(FULL_SCREEN, fullScreen);
        bundle.putBoolean(AUTO_TITLE, autoTitle);
        startActivity(WebsiteActivity.class, bundle);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, Bundle bundle, int requestCode) {
        if (noDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(mContext, clazz);
            if (bundle == null) {
                bundle = new Bundle();
            }
            intent.putExtras(bundle);
            lastClick = System.currentTimeMillis();
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 成功响应
     *
     * @param resultData 数据
     */
    @Override
    public void onLoadData(DATA resultData) {
        hideLoading();
    }

    /**
     * 失败响应
     *
     * @param resultMsg  失败返回信息
     * @param resultCode 失败返回码
     */
    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        hideLoading();
        ToastUtils.showShort(resultMsg);
    }

    public void showLoading() {
        LoadingProgressDialog.showProgressDialog(mContext);
    }

    public void showLoading(String msg) {
        LoadingProgressDialog.showProgressDialog(mContext, msg);
    }

    public void showLoading(int msg) {
        LoadingProgressDialog.showProgressDialog(mContext, mContext.getResources().getString(msg));
    }

    public void hideLoading() {
        LoadingProgressDialog.dismissProgressDialog();
    }

    protected boolean noDoubleClick() {
        return System.currentTimeMillis() - lastClick >= Config.Numbers.CLICK_LIMIT;
    }

    protected void dismiss(@Size(min = 1) TDialog... dialogs) {
        for (TDialog dialog : dialogs) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    protected void dismiss(@Size(min = 1) TPop... pops) {
        for (TPop pop : pops) {
            if (pop != null) {
                pop.dismiss();
            }
        }
    }
}
