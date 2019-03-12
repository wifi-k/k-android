package net.treebear.kwifimanager.activity.toolkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.suke.widget.SwitchButton;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.ConstConfig;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.config.Values;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class SeniorWifiSettingActivity extends BaseActivity {

    @BindView(R.id.tv_spectrum_bandwidth)
    TextView tvSpectrumBandwidth;
    @BindView(R.id.tv_work_model)
    TextView tvWorkModel;
    @BindView(R.id.swb_broadcast)
    SwitchButton swbBroadcast;
    @BindView(R.id.tv_net_channel)
    TextView tvNetChannel;
    private int netChannelPosition;
    private int workModelPosition;
    private int spectrumBandwidthPosition;

    @Override
    public int layoutId() {
        return R.layout.activity_senior_wifi_setting;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.senior_settings, R.string.save);
    }

    @Override
    protected void onTitleRightClick() {
        // TODO: 2019/3/12 save
        ToastUtils.showShort(R.string.save);
    }

    @OnClick(R.id.tv_spectrum_bandwidth)
    public void onTvSpectrumBandwidthClicked() {
//        titleText = params.getString(Keys.TITLE, "");
//        settingItemType = params.getInt(Keys.TYPE, 0);
//        currentPosition = params.getInt(Keys.POSITION, -1);
//        ArrayList<String> arr = params.getStringArrayList(Keys.SETTINGS_ITEM_DATA);
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TITLE, getString(R.string.spectrum_bandwidth));
        bundle.putInt(Keys.TYPE, Config.Types.SPECTRUM_BANDWIDTH);
        bundle.putInt(Keys.POSITION, spectrumBandwidthPosition);
        bundle.putSerializable(Keys.SETTINGS_ITEM_DATA, convert(ConstConfig.SPECTRUM_BANDWIDTH));
        startActivityForResult(SeniorSettingItemActivity.class, bundle, Values.SENIOR_SETTING);
    }

    @OnClick(R.id.tv_work_model)
    public void onTvWorkModelClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TITLE, getString(R.string.work_model));
        bundle.putInt(Keys.TYPE, Config.Types.WORK_MODEL);
        bundle.putInt(Keys.POSITION, workModelPosition);
        bundle.putSerializable(Keys.SETTINGS_ITEM_DATA, convert(ConstConfig.WORK_MODEL));
        startActivityForResult(SeniorSettingItemActivity.class, bundle, Values.SENIOR_SETTING);
    }

    @OnClick(R.id.tv_net_channel)
    public void onTvNetChannelClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TITLE, getString(R.string.net_channel));
        bundle.putInt(Keys.TYPE, Config.Types.NET_CHANNEL);
        bundle.putInt(Keys.POSITION, netChannelPosition);
        bundle.putSerializable(Keys.SETTINGS_ITEM_DATA, convert(ConstConfig.NET_CHANNEL));
        startActivityForResult(SeniorSettingItemActivity.class, bundle,Values.SENIOR_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Values.SENIOR_SETTING && resultCode == Values.SENIOR_SETTING && data != null) {
//            intent.putExtra(Keys.NAME,itemData.get(currentPosition).getName());
////            intent.putExtra(Keys.POSITION,currentPosition);
////            intent.putExtra(Keys.TYPE,settingItemType);
            int type = data.getIntExtra(Keys.TYPE, 0);
            switch (type) {
                case Config.Types.NET_CHANNEL:
                    netChannelPosition = data.getIntExtra(Keys.POSITION, 0);
                    tvNetChannel.setText(data.getStringExtra(Keys.NAME));
                    break;
                case Config.Types.WORK_MODEL:
                    workModelPosition = data.getIntExtra(Keys.POSITION, 0);
                    tvWorkModel.setText(data.getStringExtra(Keys.NAME));
                    break;
                case Config.Types.SPECTRUM_BANDWIDTH:
                    spectrumBandwidthPosition = data.getIntExtra(Keys.POSITION, 0);
                    tvSpectrumBandwidth.setText(data.getStringExtra(Keys.NAME));
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<String> convert(ArrayList<Integer> list){
        ArrayList<String> strings = new ArrayList<>();
        for (Integer integer : list) {
            strings.add(getString(integer));
        }

        return strings;
    }
}
