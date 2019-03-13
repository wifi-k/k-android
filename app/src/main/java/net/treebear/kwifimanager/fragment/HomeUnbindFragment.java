package net.treebear.kwifimanager.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.bindap.BindAction1Activity;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.config.Values;
import net.treebear.kwifimanager.util.NetWorkUtils;
import net.treebear.kwifimanager.widget.TInputDialog;

import butterknife.BindView;
import butterknife.OnClick;

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
    private TInputDialog inputDialog;

    public HomeUnbindFragment() {

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_unbind;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.string.app_name, false);
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
        initFamilyDialog();
        inputDialog.show();
    }

    private void initFamilyDialog() {
        if (inputDialog == null) {
            inputDialog = new TInputDialog(mContext);
            inputDialog.setTitle(R.string.input_family_code_into_family);
            inputDialog.setInputDialogListener(new TInputDialog.InputDialogListener() {

                @Override
                public void onLeftClick(String s) {
                    inputDialog.dismiss();
                }

                @Override
                public void onRightClick(String s) {
                    // TODO: 2019/3/7 上传判断家庭码
                    inputDialog.dismiss();
                    ToastUtils.showShort(s);
                }
            });
        }
    }
}
