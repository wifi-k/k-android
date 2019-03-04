package net.treebear.kwifimanager.fragment;


import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseFragment;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class HomeUnbindFragment extends BaseFragment {


    public HomeUnbindFragment() {

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home_unbind;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!hasLoadData && isVisibleToUser) {
            // TODO: 2019/3/4 加载数据 加载成功后将hasLoadData改为true
        }
    }

}
