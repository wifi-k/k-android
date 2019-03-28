package cn.treebear.kwifimanager.activity.toolkit;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseActivity;

/**
 * @author Administrator
 */
public class DHCPServerActivity extends BaseActivity {

    @BindView(R.id.rb_open)
    RadioButton rbOpen;
    @BindView(R.id.rb_close)
    RadioButton rbClose;
    @BindView(R.id.rb_auto)
    RadioButton rbAuto;
    @BindView(R.id.rg_dhcp_server)
    RadioGroup rgDhcpServer;
    @BindView(R.id.et_address_start)
    EditText etAddressStart;
    @BindView(R.id.et_dns2)
    EditText etDns2;
    @BindView(R.id.et_dns1)
    EditText etDns1;
    @BindView(R.id.et_gateway)
    EditText etGateway;
    @BindView(R.id.et_address_duration)
    EditText etAddressDuration;
    @BindView(R.id.et_address_end)
    EditText etAddressEnd;

    @Override
    public int layoutId() {
        return R.layout.activity_dhcpserver;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.toolkit_dhcp_server, R.string.save);
    }
}
