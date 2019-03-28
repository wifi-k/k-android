package cn.treebear.kwifimanager.activity.toolkit;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.R;
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

    @BindView(R.id.tv_choose_online_type)
    TextView tvChooseOnlineType;
    @BindView(R.id.fragment_wrapper)
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

    @Override
    protected int getFragmentHolderId() {
        return R.id.fragment_wrapper;
    }

    private void initFragment() {
        dynamicOnlineFragment = new DynamicOnlineFragment();
        staticIpFragment = new StaticIpFragment();
        pppoeFragment = new PPPOEFragment();
        addFragments(dynamicOnlineFragment, staticIpFragment, pppoeFragment);
    }

    @OnClick(R.id.tv_choose_online_type)
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
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        dismiss(tChooseOnlineTypePop);
        super.onDestroy();
    }
}
