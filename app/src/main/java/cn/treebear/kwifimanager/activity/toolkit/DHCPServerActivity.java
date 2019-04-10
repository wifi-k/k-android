package cn.treebear.kwifimanager.activity.toolkit;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseActivity;

/**
 * @author Administrator
 */
public class DHCPServerActivity extends BaseActivity {

    @BindView(R2.id.rb_open)
    RadioButton rbOpen;
    @BindView(R2.id.rb_close)
    RadioButton rbClose;
    @BindView(R2.id.rb_auto)
    RadioButton rbAuto;
    @BindView(R2.id.rg_dhcp_server)
    RadioGroup rgDhcpServer;
    @BindView(R2.id.et_address_start)
    EditText etAddressStart;
    @BindView(R2.id.et_dns2)
    EditText etDns2;
    @BindView(R2.id.et_dns1)
    EditText etDns1;
    @BindView(R2.id.et_gateway)
    EditText etGateway;
    @BindView(R2.id.et_address_duration)
    EditText etAddressDuration;
    @BindView(R2.id.et_address_end)
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
