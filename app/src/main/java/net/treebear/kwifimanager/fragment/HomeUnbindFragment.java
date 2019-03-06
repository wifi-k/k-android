package net.treebear.kwifimanager.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.bindap.BindAction1Activity;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.config.Values;
import net.treebear.kwifimanager.util.NetWorkUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link BaseFragment} subclass.
 *
 * @author Administrator
 */
public class HomeUnbindFragment extends BaseFragment {


    @BindView(R.id.iv_bind)
    ImageView ivBind;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_input_family_code)
    TextView tvInputFamilyCode;
    Unbinder unbinder;

    public HomeUnbindFragment() {

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_unbind;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.string.app_name);
    }

    @OnClick({R.id.iv_bind, R.id.tv_bind})
    public void onViewClicked(View view) {
        String wifiSSID = NetWorkUtils.getSSIDWhenWifi(mContext);
        Bundle bundle = new Bundle();
        if (NetWorkUtils.isWifiConnected(mContext)) {
            if (wifiSSID.startsWith(Config.Text.AP_NAME_START)) {
                bundle.putInt(Keys.TYPE, Values.CONNET_WIFI_XIAOK);
            } else {
                bundle.putInt(Keys.TYPE, Values.CONNECT_WIFI_OTHER);
            }
        } else {
            bundle.putInt(Keys.TYPE, Values.CONNECT_WIFI_NONE);
        }
        startActivity(BindAction1Activity.class, bundle);
    }

    @OnClick(R.id.tv_input_family_code)
    public void onFamilyCodeClicked() {

    }
}
