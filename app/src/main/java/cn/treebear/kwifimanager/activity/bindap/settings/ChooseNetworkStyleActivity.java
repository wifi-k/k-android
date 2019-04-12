package cn.treebear.kwifimanager.activity.bindap.settings;

import android.util.ArrayMap;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.WifiDeviceInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.http.ApiCode;
import cn.treebear.kwifimanager.http.WiFiHttpClient;
import cn.treebear.kwifimanager.mvp.IModel;
import cn.treebear.kwifimanager.mvp.server.model.BindNodeModel;
import cn.treebear.kwifimanager.mvp.wifi.contract.DynamicIpContract;
import cn.treebear.kwifimanager.mvp.wifi.presenter.DynamicIpPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.RequestBodyUtils;
import cn.treebear.kwifimanager.util.SharedPreferencesUtil;
import cn.treebear.kwifimanager.widget.dialog.LoadingProgressDialog;

/**
 * @author Administrator
 */
public class ChooseNetworkStyleActivity extends BaseActivity<DynamicIpContract.Presenter, WifiDeviceInfo> implements DynamicIpContract.View {

    @BindView(R2.id.rg_online_type)
    RadioGroup rgOnlineType;

    private int onlineType;

    int count = 0;

    @Override
    public int layoutId() {
        return R.layout.activity_choose_network_style;
    }

    @Override
    public DynamicIpContract.Presenter getPresenter() {
        return new DynamicIpPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        rgOnlineType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_online_type_dial:
                    onlineType = Values.ONLINE_TYPE_DIAL;
                    break;
                case R.id.rb_online_type_static:
                    onlineType = Values.ONLINE_TYPE_STATIC_IP;
                    break;
                default:
                    onlineType = Values.ONLINE_TYPE_DYNAMIC_IP;
                    break;
            }
        });
    }

    @OnClick(R2.id.btn_online_type_next)
    public void onViewClicked() {
        switch (onlineType) {
            case Values.ONLINE_TYPE_DIAL:
                startActivity(DialUpOnlineActivity.class);
                break;
            case Values.ONLINE_TYPE_STATIC_IP:
                startActivity(StaticIpOnlineActivity.class);
                break;
            default:
                LoadingProgressDialog.showProgressDialog(this, getString(R.string.try_to_connect_wifi));
                mPresenter.dynamicIpSet();
                count = 0;
                showLoading(R.string.option_ing);
                break;
        }
    }

    @Override
    public void onLoadData(WifiDeviceInfo resultData) {
        WifiDeviceInfo deviceInfo = WiFiHttpClient.getWifiDeviceInfo();
        deviceInfo.setConnect(true);
        deviceInfo.setWan(resultData.getWan());
        WiFiHttpClient.setWifiDeviceInfo(deviceInfo);
        bindNode();
    }

    private void bindNode() {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put(Keys.NODE_ID, String.valueOf(SharedPreferencesUtil.getParam(SharedPreferencesUtil.NODE_ID, "")));
        new BindNodeModel().bindNode(RequestBodyUtils.convert(map), new IModel.AsyncCallBack<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> resultData) {
                hideLoading();
                SharedPreferencesUtil.setParam(SharedPreferencesUtil.NODE_ID, "");
                MyApplication.getAppContext().getUser().setNodeSize(1);
                startActivity(ModifyWifiInfoActivity.class);
                ToastUtils.showShort(R.string.connect_success);
            }

            @Override
            public void onFailed(BaseResponse resultData, String resultMsg, int resultCode) {
                hideLoading();
                ToastUtils.showShort(R.string.dynamic_ip_set_fail);
            }
        });
    }

    @Override
    public void onLoadFail(BaseResponse response, String resultMsg, int resultCode) {
        switch (resultCode) {
            case Config.WifiResponseCode.CONNECT_FAIL:
                if (++count > 4) {
                    hideLoading();
                    ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                    return;
                }
                // 延时1秒再次查询
                rgOnlineType.postDelayed(() -> {
                    if (mPresenter != null) {
                        mPresenter.queryNetStatus();
                    }
                }, 2000);
                break;
            case Config.WifiResponseCode.CONNECT_SUCCESS:
                bindNode();
                break;
            case ApiCode.CUSTOM_ERROR:
                hideLoading();
                ToastUtils.showShort(R.string.dynamic_ip_set_fail);
                break;
            default:
                hideLoading();
                ToastUtils.showShort(R.string.connect_fail);
                break;
        }
        WiFiHttpClient.dealWithResultCode(resultCode);
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        mPresenter = null;
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}
