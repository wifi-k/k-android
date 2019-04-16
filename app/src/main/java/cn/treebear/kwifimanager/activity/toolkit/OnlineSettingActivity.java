package cn.treebear.kwifimanager.activity.toolkit;

import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.base.BaseFragmentActivity;
import cn.treebear.kwifimanager.config.ConstConfig;
import cn.treebear.kwifimanager.fragment.DynamicOnlineFragment;
import cn.treebear.kwifimanager.fragment.PPPOEFragment;
import cn.treebear.kwifimanager.fragment.StaticIpFragment;
import cn.treebear.kwifimanager.widget.pop.TChooseOnlineTypePop;

/**
 * @author Administrator
 */
public class OnlineSettingActivity extends BaseFragmentActivity {

    @BindView(R2.id.tv_choose_online_type)
    TextView tvChooseOnlineType;
    @BindView(R2.id.fragment_wrapper)
    FrameLayout fragmentWrapper;
    private TChooseOnlineTypePop tChooseOnlineTypePop;
    private DynamicOnlineFragment dynamicOnlineFragment;
    private StaticIpFragment staticIpFragment;
    private PPPOEFragment pppoeFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_online_setting;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.toolkit_online_setting);
        initFragment();
    }

    private void initFragment() {
        dynamicOnlineFragment = new DynamicOnlineFragment();
        staticIpFragment = new StaticIpFragment();
        pppoeFragment = new PPPOEFragment();
        addFragments(R.id.fragment_wrapper, dynamicOnlineFragment, staticIpFragment, pppoeFragment);
    }

    @OnClick(R2.id.tv_choose_online_type)
    public void onViewClicked() {
        if (tChooseOnlineTypePop == null) {
            tChooseOnlineTypePop = new TChooseOnlineTypePop(this);
            tChooseOnlineTypePop.setOnChooseTypeList(new TChooseOnlineTypePop.OnChooseTypeListener() {
                @Override
                public void onClickItem(int position) {
                    tvChooseOnlineType.setText(ConstConfig.ONLINE_TIME_TYPE.get(position));
                    updateFragment(position);
                }
            });
        }
        tChooseOnlineTypePop.show(tvChooseOnlineType);
    }

    @Override
    protected void onDestroy() {
        dismiss(tChooseOnlineTypePop);
        super.onDestroy();
    }
}
