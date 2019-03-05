package net.treebear.kwifimanager.activity.wifisetting;

import android.widget.RadioGroup;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.config.Config;
import net.treebear.kwifimanager.config.Values;
import net.treebear.kwifimanager.util.ActivityStackUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class ChooseNetworkStyleActivity extends BaseActivity {

    @BindView(R.id.rg_online_type)
    RadioGroup rgOnlineType;

    private int onlineType;

    @Override
    public int layoutId() {
        return R.layout.activity_choose_network_style;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.setting);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
        rgOnlineType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
            }
        });
    }

    @OnClick(R.id.btn_online_type_next)
    public void onViewClicked() {
        switch (onlineType) {
            case Values.ONLINE_TYPE_DIAL:
                startActivity(DialUpOnlineActivity.class);
                break;
            case Values.ONLINE_TYPE_STATIC_IP:
                startActivity(StaticIPOnlineActivity.class);
                break;
            default:
                startActivity(DynamicIPOnlineActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtils.popActivity(Config.Tags.TAG_FIRST_BIND_WIFI, this);
    }
}
